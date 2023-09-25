package com.jar.service.system.container.service.jpa.mapper;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.container.service.domian.entity.Container;
import com.jar.service.system.container.service.jpa.entity.ContainerEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContainerDataAccessMapper {

    public ContainerEntity convertContainerToContainerEntity(Container container) {
        return ContainerEntity.builder()
                .containerId(container.getId().getValue())
                .imageId(container.getImageId())
                .javaVersion(container.getJavaVersion())
                .containerStatus(container.getContainerStatus())
                .appOrderId(container.getAppOrderId().getValue())
                .dockerContainerId(container.getDockerContainerId().getValue())
                .applicationName(container.getApplicationName())
                .error(Optional.ofNullable(container.getError()).orElse(""))
                .s3URL(container.getS3URL())
                .serverPort(container.getServerPort())
                .build();
    }

    public Container convertContainerEntityToContainer(ContainerEntity containerEntity) {
        return Container.builder()
                .containerId(new ContainerId(containerEntity.getContainerId()))
                .imageId(containerEntity.getImageId())
                .javaVersion(containerEntity.getJavaVersion())
                .containerStatus(containerEntity.getContainerStatus())
                .appOrderId(new AppOrderId(containerEntity.getAppOrderId()))
                .dockerContainerId(new DockerContainerId(containerEntity.getDockerContainerId()))
                .error(Optional.ofNullable(containerEntity.getError()).orElse(""))
                .applicationName(containerEntity.getApplicationName())
                .s3URL(containerEntity.getS3URL())
                .serverPort(containerEntity.getServerPort())
                .build();
    }
}
