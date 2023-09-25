package com.jar.service.system.apporder.service.domain.entity;

import com.jar.service.system.apporder.service.domain.valueobject.ContainerConfig;
import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Container extends AggregateRoot<ContainerId> {

    private final AppOrderId appOrderId;
    private final ContainerStatus containerStatus;
    private final String error;

    private final String applicationName;

    private final DockerContainerId dockerContainerId;

    private final Integer javaVersion;
    private final String s3URL;

    private final Integer serverPort;

    @Builder
    private Container(ContainerId containerId, AppOrderId appOrderId, ContainerStatus containerStatus,
                      String error, String applicationName, DockerContainerId dockerContainerId,
                      Integer javaVersion, String s3URL, Integer serverPort) {
        super.setId(containerId);
        this.appOrderId = appOrderId;
        this.applicationName = applicationName;
        this.dockerContainerId = dockerContainerId;
        this.javaVersion = javaVersion;
        this.s3URL = s3URL;
        this.serverPort = serverPort;
        this.error = error;
        this.containerStatus = containerStatus;
    }

    public static Container initialize(AppOrderId appOrderId, Integer javaVersion,
                                       ContainerConfig containerConfig) {
        return Container.builder()
                .appOrderId(appOrderId)
                .containerId(new ContainerId(UUID.randomUUID()))
                .containerStatus(ContainerStatus.INITIALIZED)
                .s3URL(containerConfig.getS3URL())
                .applicationName(containerConfig.getApplicationName())
                .javaVersion(javaVersion)
                .dockerContainerId(new DockerContainerId(""))
                .error("")
                .serverPort(containerConfig.getServerPort())
                .build();
    }
}
