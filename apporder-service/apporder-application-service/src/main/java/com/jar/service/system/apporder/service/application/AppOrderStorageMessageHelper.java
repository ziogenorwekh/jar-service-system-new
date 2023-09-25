package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.apporder.service.application.mapper.AppOrderDataMapper;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.domain.event.AppOrderContainerCreationApprovalEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderEvent;
import com.jar.service.system.apporder.service.domain.valueobject.ContainerConfig;
import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.BaseId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class AppOrderStorageMessageHelper extends AppOrderMessageHelper {

    private final AppOrderDomainService appOrderDomainService;
    private final AppOrderRepository appOrderRepository;
    private final AppOrderDataMapper appOrderDataMapper;
    private final StorageRepository storageRepository;
    private final ContainerRepository containerRepository;

    @Autowired
    public AppOrderStorageMessageHelper(AppOrderDomainService appOrderDomainService,
                                        AppOrderRepository appOrderRepository,
                                        AppOrderDataMapper appOrderDataMapper,
                                        StorageRepository storageRepository,
                                        ContainerRepository containerRepository) {
        super(appOrderDomainService, appOrderRepository);
        this.appOrderDomainService = appOrderDomainService;
        this.appOrderRepository = appOrderRepository;
        this.appOrderDataMapper = appOrderDataMapper;
        this.storageRepository = storageRepository;
        this.containerRepository = containerRepository;
    }

    @Transactional
    public AppOrderEvent<? extends AggregateRoot<? extends BaseId<UUID>>> saveStorageHelper(
            StorageApprovalResponse storageApprovalResponse) {
        log.trace("StorageApprovalResponse : {}", storageApprovalResponse.toString());
        AppOrder appOrder = findAppOrder(storageApprovalResponse.getAppOrderId());
        try {
            Storage storage = appOrderDataMapper
                    .convertStorageApprovalResponseToStorage(storageApprovalResponse);
            saveStorage(storage, appOrder);
            return initializeContainer(storageApprovalResponse);
        } catch (Exception e) {
            return rejectProcessingEvent(appOrder, e.getMessage());
        }
    }

    private AppOrderContainerCreationApprovalEvent initializeContainer(
            StorageApprovalResponse storageApprovalResponse) {
        AppOrder appOrder = findAppOrder(storageApprovalResponse.getAppOrderId());

        ContainerConfig containerConfig = appOrderDataMapper.
                convertServerConfigToContainerConfig(appOrder.getServerConfig(),
                        storageApprovalResponse.getFileUrl());

        AppOrderContainerCreationApprovalEvent appOrderContainerCreationApprovalEvent =
                appOrderDomainService.initializeAppOrderContainer(appOrder, containerConfig);
        Container container = appOrderContainerCreationApprovalEvent.getDomainType();
        saveContainer(appOrder, container);
        return appOrderContainerCreationApprovalEvent;
    }


    private void saveContainer(AppOrder appOrder, Container container) {
        log.trace("save Container is AppOrder data endPoint : {}", appOrder.getServerConfig().getEndPoint());
        AppOrder save = appOrderRepository.save(appOrder);
        containerRepository.save(container);
    }

    private void saveStorage(Storage storage, AppOrder appOrder) {
        appOrderDomainService.successfulCreationStorage(appOrder, storage);

        AppOrder save = appOrderRepository.save(appOrder);
        storageRepository.save(storage);
    }

}
