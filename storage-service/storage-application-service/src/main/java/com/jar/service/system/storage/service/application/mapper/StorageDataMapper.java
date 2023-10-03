package com.jar.service.system.storage.service.application.mapper;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.StorageId;
import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateCommand;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateResponse;
import com.jar.service.system.storage.service.application.dto.delete.StorageDeleteCommand;
import com.jar.service.system.storage.service.application.dto.message.AppOrderCreatedResponse;
import com.jar.service.system.storage.service.application.dto.message.StorageDeleteApprovalResponse;
import com.jar.service.system.storage.service.domain.entity.AppOrder;
import com.jar.service.system.storage.service.domain.entity.Storage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StorageDataMapper {

    public Storage convertStorageCreateCommandToStorage(StorageCreateCommand storageCreateCommand) {
        return Storage.builder()
                .userId(new UserId(storageCreateCommand.getUserId()))
                .appOrderId(new AppOrderId(storageCreateCommand.getAppOrderId()))
                .build();
    }

    public StorageCreateResponse convertStorageToStorageCreateResponse(Storage storage) {
        return StorageCreateResponse.builder()
                .appOrderId(storage.getAppOrderId().getValue())
                .filename(storage.getFilename())
                .storageStatus(storage.getStorageStatus())
                .fileType(storage.getFileType())
                .build();
    }

    public Storage convertStorageDeleteApprovalResponseToStorage(
            StorageDeleteApprovalResponse storageDeleteApprovalResponse) {
        return Storage.builder()
                .appOrderId(new AppOrderId(storageDeleteApprovalResponse.getAppOrderId()))
                .build();
    }

    public AppOrder convertAppOrderCreatedResponseToAppOrder(AppOrderCreatedResponse appOrderCreatedResponse) {
        return AppOrder.builder().appOrderId(new AppOrderId(appOrderCreatedResponse.getAppOrderId())).build();
    }

    public AppOrder convertStorageDeleteApprovalResponseToAppOrder(
            StorageDeleteApprovalResponse storageDeleteApprovalResponse) {
        return AppOrder.builder().appOrderId(new AppOrderId(storageDeleteApprovalResponse.getAppOrderId()))
                .build();
    }
    public AppOrder convertStorageDeleteCommandToAppOrder(
            StorageDeleteCommand storageDeleteCommand) {
        return AppOrder.builder().appOrderId(new AppOrderId(storageDeleteCommand.getAppOrderId()))
                .build();
    }
}
