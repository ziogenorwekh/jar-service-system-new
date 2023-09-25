package com.jar.service.system.storage.service.kafka.mapper;

import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.common.avro.model.StorageAvroModel;
import com.jar.service.system.common.avro.model.StorageStatus;
import com.jar.service.system.storage.service.application.dto.message.AppOrderCreatedResponse;
import com.jar.service.system.storage.service.application.dto.message.StorageDeleteApprovalResponse;
import com.jar.service.system.storage.service.domain.entity.Storage;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class StorageMessageMapper {


    public StorageAvroModel convertStorageToStorageAvroModel(Storage storage) {
        return StorageAvroModel.newBuilder()
                .setStorageId(storage.getId().getValue().toString())
                .setAppOrderId(storage.getAppOrderId().getValue().toString())
                .setError(Optional.ofNullable(storage.getError()).orElse(""))
                .setFilename(Optional.ofNullable(storage.getFilename()).orElse(""))
                .setStorageStatus(StorageStatus.valueOf(storage.getStorageStatus().name()))
                .setFileType(Optional.ofNullable(storage.getFileType()).orElse(""))
                .setFileUrl(Optional.ofNullable(storage.getFileUrl()).orElse(""))
                .build();
    }

    public AppOrderCreatedResponse convertAppOrderAvroModelToAppOrderCreatedResponse(
            AppOrderAvroModel appOrderAvroModel) {
        return AppOrderCreatedResponse.builder().
                appOrderId(UUID.fromString(appOrderAvroModel.getAppOrderId()))
                .build();
    }

    public StorageDeleteApprovalResponse convertAppOrderAvroModelToStorageDeleteApprovalResponse(
            AppOrderAvroModel appOrderAvroModel) {
        return StorageDeleteApprovalResponse.builder()
                .appOrderId(UUID.fromString(appOrderAvroModel.getAppOrderId())).build();
    }
}
