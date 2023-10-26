package com.jar.service.system.apporder.service.domain.entity;

import com.jar.service.system.apporder.service.domain.exception.AppOrderDomainException;
import com.jar.service.system.apporder.service.domain.valueobject.ContainerConfig;
import com.jar.service.system.apporder.service.domain.valueobject.ServerConfig;
import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.*;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

// need to search user, database
@Getter
public class AppOrder extends AggregateRoot<AppOrderId> {

    private UserId userId;
    private StorageId storageId;
    private ServerConfig serverConfig;
    private ContainerId containerId;
    private ApplicationStatus applicationStatus;
    private String errorResult;


    @Builder
    private AppOrder(AppOrderId appOrderId, UserId userId,
                     ServerConfig serverConfig, StorageId storageId, ContainerId containerId,
                     ApplicationStatus applicationStatus, String errorResult) {
        super.setId(appOrderId);
        this.userId = userId;
        this.serverConfig = serverConfig;
        this.applicationStatus = applicationStatus;
        this.storageId = storageId;
        this.containerId = containerId;
        this.errorResult = errorResult;
    }

    public void initializeAppOrder(User user) {
        isExistUser(user);
        validateServerConfig();
        super.setId(new AppOrderId(UUID.randomUUID()));
        this.userId = user.getId();
        this.applicationStatus = ApplicationStatus.PENDING;
    }


    public Container initializeContainer(ContainerConfig containerConfig) {
        validateInitializeContainer();
        this.applicationStatus = ApplicationStatus.CONTAINERIZING;
        Container initialize = Container.initialize(this.getId(), this.getServerConfig()
                .getJavaVersion(), containerConfig);
        this.containerId = initialize.getId();
        return initialize;
    }

    /**
     * If Storage create successful, status is change it's condition and save storageId
     */
    public void saveStorage(Storage storage) {
        validateSaveStorage(storage);
        this.storageId = storage.getId();
        this.applicationStatus = ApplicationStatus.SAVED;
    }

    public void startedContainer(Container container, ServerConfig serverConfig) {
        validateStartedContainer(container);
        this.serverConfig = serverConfig;
        this.applicationStatus = ApplicationStatus.COMPLETE;
        this.containerId = container.getId();
    }

    public void addErrorMessage(String error) {
        this.errorResult = error;
        this.applicationStatus = ApplicationStatus.FAILED;
    }

    private void validateInitializeContainer() {
        if (storageId == null || !applicationStatus.equals(ApplicationStatus.SAVED)) {
            this.errorResult = "Incorrect build order. storage is not SAVED.";
            this.applicationStatus = ApplicationStatus.FAILED;
            throw new AppOrderDomainException("Incorrect build order.");
        }
    }

    private void validateSaveStorage(Storage storage) {
        if (storage.getStorageStatus() == StorageStatus.REJECTED) {
            this.applicationStatus = ApplicationStatus.FAILED;
            this.errorResult = String.format("Storage is not SAVED. Actual is : %s",
                    storage.getStorageStatus());
            throw new AppOrderDomainException(String.format("Storage is not SAVED. Actual is : %s",
                    storage.getStorageStatus()));
        }
    }
    private void validateServerConfig() {
        if (serverConfig.getServerPort() == null) {
            throw new AppOrderDomainException("server port must be necessary.");
        }
        if (serverConfig.getApplicationName() == null) {
            throw new AppOrderDomainException("application name must be necessary.");
        }
    }

    private void isExistUser(User user) {
        if (user.getId() == null) {
            throw new AppOrderDomainException("User not found.");
        }
    }

    private void validateStartedContainer(Container container) {
        if (container.getId().getValue() == null) {
            throw new AppOrderDomainException("container is not saved.");
        }
        if (!container.getContainerStatus().equals(ContainerStatus.STARTED)) {
            this.errorResult = "Container is not STARTED.";
            this.applicationStatus = ApplicationStatus.FAILED;
            throw new AppOrderDomainException("Container is not STARTED.");
        }
        if (!this.applicationStatus.equals(ApplicationStatus.CONTAINERIZING)) {
            this.errorResult = "AppOrder is not CONTAINERIZING.";
            this.applicationStatus = ApplicationStatus.FAILED;
            throw new AppOrderDomainException("AppOrder is not CONTAINERIZING.");
        }
    }

    @Override
    public String toString() {
        return "AppOrder{" +
                "userId=" + userId +
                ", storageId=" + storageId +
                ", serverConfig=" + serverConfig +
                ", containerId=" + containerId +
                ", applicationStatus=" + applicationStatus +
                ", errorResult='" + errorResult + '\'' +
                '}';
    }
}
