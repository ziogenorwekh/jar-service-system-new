package com.jar.service.system.apporder.service.jpa.mapper;

import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.jpa.entity.AppOrderEntity;
import com.jar.service.system.apporder.service.jpa.entity.StorageEntity;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.StorageId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StorageDataAccessMapper {

    public StorageEntity convertStorageEntityToStorage(Storage storage) {
        return StorageEntity.builder()
                .appOrderId(storage.getAppOrderId().getValue())
                .error(Optional.ofNullable(storage.getError()).orElse(""))
                .filename(storage.getFilename())
                .fileType(storage.getFileType())
                .storageId(storage.getId().getValue())
                .fileUrl(storage.getFileUrl())
                .storageStatus(storage.getStorageStatus())
                .build();
    }

    public Storage convertStorageEntityToStorage(StorageEntity storageEntity) {
        return Storage.builder()
                .appOrderId(new AppOrderId(storageEntity.getAppOrderId()))
                .error(Optional.ofNullable(storageEntity.getError()).orElse(""))
                .filename(storageEntity.getFilename())
                .fileType(storageEntity.getFileType())
                .storageId(new StorageId(storageEntity.getStorageId()))
                .fileUrl(storageEntity.getFileUrl())
                .storageStatus(storageEntity.getStorageStatus())
                .build();
    }
}
