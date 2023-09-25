package com.jar.service.system.container.service.application.mapper;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.container.service.application.dto.connect.DockerCreateResponse;
import com.jar.service.system.container.service.application.dto.connect.DockerUsage;
import com.jar.service.system.container.service.application.dto.message.ContainerCreationApprovalResponse;
import com.jar.service.system.container.service.application.dto.track.TrackContainerResponse;
import com.jar.service.system.container.service.application.dto.update.ContainerUpdateResponse;
import com.jar.service.system.container.service.domian.entity.Container;
import com.jar.service.system.container.service.domian.valueobject.DockerConfig;
import org.springframework.stereotype.Component;

@Component
public class ContainerDataMapper {

    public Container convertContainerApprovalResponseToContainer(ContainerCreationApprovalResponse containerCreationApprovalResponse) {
        return Container.builder()
                .dockerContainerId(new DockerContainerId(containerCreationApprovalResponse.getDockerContainerId()))
                .appOrderId(new AppOrderId(containerCreationApprovalResponse.getAppOrderId()))
                .containerId(new ContainerId(containerCreationApprovalResponse.getContainerId()))
                .containerStatus(containerCreationApprovalResponse.getContainerStatus())
                .error(containerCreationApprovalResponse.getError())
                .javaVersion(containerCreationApprovalResponse.getJavaVersion())
                .s3URL(containerCreationApprovalResponse.getS3URL())
                .serverPort(containerCreationApprovalResponse.getServerPort())
                .applicationName(containerCreationApprovalResponse.getApplicationName())
                .build();
    }

    public DockerConfig convertDockerResponseToDockerConfig(DockerCreateResponse dockerCreateResponse) {
        return DockerConfig.builder()
                .dockerContainerId(dockerCreateResponse.getDockerContainerId())
                .imageId(dockerCreateResponse.getImage())
                .failureMessage(dockerCreateResponse.getError() == null ? "" : dockerCreateResponse.getError())
                .build();
    }

    public ContainerUpdateResponse convertContainerUpdateResponse(Container container) {
        return ContainerUpdateResponse.builder()
                .containerStatus(container.getContainerStatus())
                .applicationName(container.getApplicationName())
                .build();
    }

    public TrackContainerResponse convertDockerUsageTrackContainerResponse(DockerUsage dockerUsage,
                                                                           Container container) {
        return TrackContainerResponse.builder()
                .dockerContainerId(container.getDockerContainerId().getValue())
                .containerId(container.getId().getValue())
                .cpuUsage(dockerUsage.getCpuUsage())
                .memoryUsage(dockerUsage.getMemoryUsage())
                .build();
    }
}
