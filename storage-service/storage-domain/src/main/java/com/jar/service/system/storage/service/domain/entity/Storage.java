package com.jar.service.system.storage.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.StorageId;
import com.jar.service.system.common.domain.valueobject.StorageStatus;
import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.storage.service.domain.exception.StorageDomainException;
import com.jar.service.system.storage.service.domain.valueobject.StorageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Storage extends AggregateRoot<StorageId> {

    private String filename;
    private String fileUrl;
    private final UserId userId;
    private String fileType;
    private StorageStatus storageStatus;
    private String error;
    private final AppOrderId appOrderId;

    @Builder
    public Storage(StorageId storageId, String filename, String fileUrl, UserId userId, String fileType,
                   StorageStatus storageStatus, AppOrderId appOrderId, String error) {
        super.setId(storageId);
        this.userId = userId;
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.storageStatus = storageStatus;
        this.appOrderId = appOrderId;
        this.error = error;
    }

    public void initialize() {
        super.setId(new StorageId(UUID.randomUUID()));
        this.storageStatus = StorageStatus.INITIALIZED;
    }


    public void saveSuccessfulStorage(StorageInfo storageInfo) {
        isInitialized(this.storageStatus);
        this.filename = storageInfo.getFilename();
        this.fileType = storageInfo.getFileType();
        this.fileUrl = storageInfo.getFileUrl();
        this.storageStatus = StorageStatus.SAVED;
    }

    public void rejectedSaveStorage(String errorMessage) {
        isInitialized(this.storageStatus);
        this.error = errorMessage;
        this.storageStatus = StorageStatus.REJECTED;
    }

    private void isInitialized(StorageStatus storageStatus) {
        if (storageStatus != StorageStatus.INITIALIZED) {
            throw new StorageDomainException("Storage state is not INITIALIZED.");
        }
    }

}
