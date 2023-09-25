package com.jar.service.system.apporder.service.jpa.mapper;

import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.jpa.entity.AppOrderEntity;
import com.jar.service.system.apporder.service.jpa.entity.ContainerEntity;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContainerDataAccessMapper {

    public ContainerEntity convertContainerToContainerEntity(Container container) {
        return ContainerEntity.builder()
                .appOrderId(container.getAppOrderId().getValue())
                .containerId(container.getId().getValue() == null ? null : container.getId().getValue())
                .containerStatus(container.getContainerStatus())
                .error(Optional.ofNullable(container.getError()).orElse(""))
                .s3URL(container.getS3URL())
                .applicationName(container.getApplicationName())
                .dockerContainerId(container.getDockerContainerId().getValue() == null ? "" :
                        container.getDockerContainerId().getValue())
                .serverPort(container.getServerPort())
                .javaVersion(container.getJavaVersion())
                .build();
    }

    public Container convertContainerToContainerEntity(ContainerEntity containerEntity) {
        return Container.builder()
                .appOrderId(new AppOrderId(containerEntity.getAppOrderId()))
                .containerId(new ContainerId(containerEntity.getContainerId()))
                .containerStatus(containerEntity.getContainerStatus())
                .error(Optional.ofNullable(containerEntity.getError()).orElse(""))
                .s3URL(containerEntity.getS3URL())
                .applicationName(containerEntity.getApplicationName())
                .dockerContainerId(containerEntity.getDockerContainerId() == null ?
                        new DockerContainerId("") :
                        new DockerContainerId(containerEntity.getDockerContainerId()))
                .serverPort(containerEntity.getServerPort())
                .javaVersion(containerEntity.getJavaVersion())
                .build();
    }

}
