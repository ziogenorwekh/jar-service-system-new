package com.jar.service.system.container.service.kafka.mapper;

import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.common.avro.model.ContainerAvroModel;
import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.container.service.application.dto.message.ContainerCreationApprovalResponse;
import com.jar.service.system.container.service.application.dto.message.ContainerDeleteApprovalResponse;
import com.jar.service.system.container.service.domian.entity.Container;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ContainerMessageMapper {


    public ContainerCreationApprovalResponse convertContainerAvroModelToContainerApprovalResponse(
            ContainerAvroModel containerAvroModel) {
        return ContainerCreationApprovalResponse.builder()
                .containerId(UUID.fromString(containerAvroModel.getContainerId()))
                .dockerContainerId(containerAvroModel.getDockerContainerId())
                .applicationName(containerAvroModel.getApplicationName())
                .containerStatus(ContainerStatus.valueOf(containerAvroModel.getContainerStatus().name()))
                .appOrderId(UUID.fromString(containerAvroModel.getAppOrderId()))
                .error(Optional.ofNullable(containerAvroModel.getError()).orElse(""))
                .javaVersion(containerAvroModel.getJavaVersion())
                .s3URL(containerAvroModel.getS3URL())
                .serverPort(containerAvroModel.getServerPort())
                .build();
    }

    public ContainerAvroModel convertContainerEventToContainerAvroModel(DomainEvent<Container> domainEvent) {
        Container container = domainEvent.getDomainType();
        return ContainerAvroModel.newBuilder()
                .setApplicationName(container.getApplicationName())
                .setContainerId(container.getId().getValue().toString())
                .setDockerContainerId(container.getDockerContainerId().getValue() == null ? "" :
                        container.getDockerContainerId().getValue())
                .setContainerStatus(com.jar.service.system.common.avro.model.ContainerStatus
                        .valueOf(container.getContainerStatus().name()))
                .setError(Optional.ofNullable(container.getError()).orElse(""))
                .setAppOrderId(container.getAppOrderId().getValue().toString())
                .setJavaVersion(container.getJavaVersion())
                .setServerPort(container.getServerPort())
                .build();
    }

    public ContainerDeleteApprovalResponse convertAppOrderAvroModelToContainerDeleteApprovalResponse(
            AppOrderAvroModel appOrderAvroModel) {
        return ContainerDeleteApprovalResponse.builder()
                .appOrderId(UUID.fromString(appOrderAvroModel.getAppOrderId())).build();
    }

}
