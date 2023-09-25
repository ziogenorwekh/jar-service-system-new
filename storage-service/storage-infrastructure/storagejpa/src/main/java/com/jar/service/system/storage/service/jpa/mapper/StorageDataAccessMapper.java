package com.jar.service.system.storage.service.jpa.mapper;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.StorageId;
import com.jar.service.system.storage.service.domain.entity.AppOrder;
import com.jar.service.system.storage.service.domain.entity.Storage;
import com.jar.service.system.storage.service.jpa.entity.AppOrderEntity;
import com.jar.service.system.storage.service.jpa.entity.StorageEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StorageDataAccessMapper {

    public StorageEntity convertStorageToStorageEntity(Storage storage) {
        return StorageEntity.builder()
                .appOrderId(storage.getAppOrderId().getValue())
                .storageId(storage.getId().getValue())
                .error(Optional.ofNullable(storage.getError()).orElse(""))
                .filename(storage.getFilename())
                .fileType(storage.getFileType())
                .storageStatus(storage.getStorageStatus())
                .fileUrl(storage.getFileUrl())
                .build();
    }

    public Storage convertStorageEntityToStorage(StorageEntity storageEntity) {
        return Storage.builder()
                .appOrderId(new AppOrderId(storageEntity.getAppOrderId()))
                .filename(storageEntity.getFilename())
                .storageId(new StorageId(storageEntity.getStorageId()))
                .fileType(storageEntity.getFileType())
                .fileUrl(storageEntity.getFileUrl())
                .storageStatus(storageEntity.getStorageStatus())
                .error(storageEntity.getError())
                .build();
    }

    public AppOrderEntity convertAppOrderToAppOrderEntity(AppOrder appOrder) {
        return AppOrderEntity.builder().appOrderId(appOrder.getId().getValue()).build();
    }

    public AppOrder convertAppOrderEntityToAppOrder(AppOrderEntity appOrderEntity) {
        return AppOrder.builder().appOrderId(new AppOrderId(appOrderEntity.getAppOrderId()))
                .build();
    }
}
