package com.jar.service.system.user.service.kafka.mapper;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.UserDeleteApprovalResponse;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.domain.event.AppOrderContainerCreationApprovalEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderCreatedEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderDeletedEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderFailedEvent;
import com.jar.service.system.common.avro.model.*;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.StorageStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AppOrderMessageMapper {


    public AppOrderAvroModel convertAppOrderCreatedEventToAppOrderAvroModel(AppOrderCreatedEvent appOrderCreatedEvent) {
        AppOrder appOrder = appOrderCreatedEvent.getDomainType();
        com.jar.service.system.common.domain.valueobject.MessageType messageType = appOrderCreatedEvent.getMessageType();
        return appOrderAvroModelBuilder(appOrder,messageType);
    }

    public AppOrderAvroModel convertAppOrderDeletedEventToAppOrderAvroModel(AppOrderDeletedEvent appOrderDeletedEvent) {
        AppOrder appOrder = appOrderDeletedEvent.getDomainType();
        com.jar.service.system.common.domain.valueobject.MessageType messageType = appOrderDeletedEvent.getMessageType();
        return appOrderAvroModelBuilder(appOrder, messageType);
    }

    public AppOrderAvroModel convertAppOrderFailedEventToAppOrderAvroModel(AppOrderFailedEvent appOrderFailedEvent) {
        AppOrder appOrder = appOrderFailedEvent.getDomainType();
        com.jar.service.system.common.domain.valueobject.MessageType messageType =
                appOrderFailedEvent.getMessageType();
        return appOrderAvroModelBuilder(appOrder,messageType);
    }

    public ContainerAvroModel convertAppOrderContainerCreationApprovalEventToContainerAvroModel(
            AppOrderContainerCreationApprovalEvent appOrderContainerCreationApprovalEvent) {
        Container container = appOrderContainerCreationApprovalEvent.getDomainType();
        return ContainerAvroModel.newBuilder()
                .setAppOrderId(container.getAppOrderId().getValue().toString())
                .setContainerId(container.getId().getValue().toString())
                .setApplicationName(container.getApplicationName())
                .setS3URL(container.getS3URL())
                .setContainerStatus(com.jar.service.system.common.avro.model.
                        ContainerStatus.valueOf(container.getContainerStatus().name()))
                .setDockerContainerId(container.getDockerContainerId().getValue() == null ? "" :
                        container.getDockerContainerId().getValue())
                .setError("")
                .setJavaVersion(container.getJavaVersion())
                .setServerPort(container.getServerPort())
                .build();
    }

    public StorageApprovalResponse convertStorageAvroModelToStorageApprovalResponse(StorageAvroModel storageAvroModel) {
        return StorageApprovalResponse.builder()
                .appOrderId(UUID.fromString(storageAvroModel.getAppOrderId()))
                .storageId(UUID.fromString(storageAvroModel.getStorageId()))
                .error(Optional.ofNullable(storageAvroModel.getError()).orElse(""))
                .filename(storageAvroModel.getFilename())
                .storageStatus(StorageStatus.valueOf(storageAvroModel.getStorageStatus().name()))
                .fileType(storageAvroModel.getFileType())
                .fileUrl(storageAvroModel.getFileUrl())
                .build();
    }

    public ContainerApprovalResponse convertContainerApprovalResponseToContainerAvroModel(
            ContainerAvroModel containerAvroModel) {
        return ContainerApprovalResponse.builder()
                .appOrderId(UUID.fromString(containerAvroModel.getAppOrderId()))
                .containerId(UUID.fromString(containerAvroModel.getContainerId()))
                .applicationName(containerAvroModel.getApplicationName())
                .containerStatus(ContainerStatus.valueOf(containerAvroModel.getContainerStatus().name()))
                .error(Optional.ofNullable(containerAvroModel.getError()).orElse(""))
                .dockerContainerId(containerAvroModel.getDockerContainerId())
                .serverPort(containerAvroModel.getServerPort())
                .javaVersion(containerAvroModel.getJavaVersion())
                .build();

    }

    public UserDeleteApprovalResponse convertUserAvroModelToUserDeleteApprovalResponse(UserAvroModel userAvroModel) {
        return UserDeleteApprovalResponse.builder()
                .userId(UUID.fromString(userAvroModel.getUserId()))
                .build();
    }

    private AppOrderAvroModel appOrderAvroModelBuilder(AppOrder appOrder, com.jar.service.system
            .common.domain.valueobject.MessageType messageType) {
        return AppOrderAvroModel.newBuilder()
                .setAppOrderId(appOrder.getId().getValue().toString())
                .setApplicationName(appOrder.getServerConfig().getApplicationName())
                .setApplicationStatus(ApplicationStatus.valueOf(appOrder.getApplicationStatus().name()))
                .setErrorResult(Optional.ofNullable(appOrder.getErrorResult()).orElse(""))
                .setServerPort(appOrder.getServerConfig().getServerPort())
                .setServerUrl(appOrder.getServerConfig().getEndPoint())
                .setContainerId(Optional.ofNullable(appOrder.getContainerId())
                        .map(Object::toString)
                        .orElse(""))
                .setStorageId(Optional.ofNullable(appOrder.getStorageId())
                        .map(Object::toString)
                        .orElse(""))
                .setMessageType(MessageType.valueOf(messageType.name()))
                .build();
    }


}
