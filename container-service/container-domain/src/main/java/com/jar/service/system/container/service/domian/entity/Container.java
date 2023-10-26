package com.jar.service.system.container.service.domian.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.container.service.domian.exception.ContainerDomainException;
import com.jar.service.system.container.service.domian.valueobject.DockerConfig;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Container extends AggregateRoot<ContainerId> {

    private final AppOrderId appOrderId;
    private ContainerStatus containerStatus;

    private DockerContainerId dockerContainerId;
    private final String applicationName;
    private String error;
    private final Integer javaVersion;
    private final String s3URL;
    private final Integer serverPort;

    private String imageId;

    @Builder
    public Container(ContainerId containerId, AppOrderId appOrderId, ContainerStatus containerStatus,
                     DockerContainerId dockerContainerId, String applicationName,
                     String error, Integer javaVersion, String s3URL, Integer serverPort, String imageId) {
        this.dockerContainerId = dockerContainerId;
        this.applicationName = applicationName;
        this.serverPort = serverPort;
        this.imageId = imageId;
        super.setId(containerId);
        this.appOrderId = appOrderId;
        this.containerStatus = containerStatus;
        this.error = error;
        this.javaVersion = javaVersion;
        this.s3URL = s3URL;
    }

    public void successfulContainerizing(DockerConfig dockerConfig) {
        if (this.containerStatus != ContainerStatus.INITIALIZED) {
            throw new ContainerDomainException("Container is not initialized.");
        }
        this.dockerContainerId = dockerConfig.getDockerContainerId();
        this.imageId = dockerConfig.getImageId();
        this.containerStatus = ContainerStatus.STARTED;
    }

    public void rejectedContainerizing(String reason) {
        if (this.containerStatus != ContainerStatus.INITIALIZED) {
            throw new ContainerDomainException("Container is not initialized.");
        }
        this.containerStatus = ContainerStatus.REJECTED;
        this.error = reason;
    }

    public void startContainer(ContainerStatus containerStatus) {
        if (this.containerStatus == ContainerStatus.STARTED) {
            throw new ContainerDomainException("Container already started.");
        }
        this.containerStatus = containerStatus;
    }

    public void stopContainer(ContainerStatus containerStatus) {
        if (this.containerStatus == ContainerStatus.STOPPED) {
            throw new ContainerDomainException("Container already stopped.");
        }
        this.containerStatus = containerStatus;
    }

    @Override
    public String toString() {
        return "Container{" +
                "appOrderId=" + appOrderId +
                ", containerStatus=" + containerStatus +
                ", dockerContainerId=" + dockerContainerId +
                ", applicationName='" + applicationName + '\'' +
                ", error='" + error + '\'' +
                ", javaVersion=" + javaVersion +
                ", s3URL='" + s3URL + '\'' +
                ", serverPort=" + serverPort +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
