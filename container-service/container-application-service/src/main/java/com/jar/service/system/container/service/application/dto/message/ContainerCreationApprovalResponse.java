package com.jar.service.system.container.service.application.dto.message;


import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ContainerCreationApprovalResponse {

    private final UUID containerId;
    private final UUID appOrderId;
    private final ContainerStatus containerStatus;
    private final String error;
    private final Integer javaVersion;
    private final String s3URL;
    private final Integer serverPort;
    private final String applicationName;
    private final String dockerContainerId;

    @Override
    public String toString() {
        return "ContainerCreationApprovalResponse{" +
                "containerId=" + containerId +
                ", appOrderId=" + appOrderId +
                ", containerStatus=" + containerStatus +
                ", error='" + error + '\'' +
                ", javaVersion=" + javaVersion +
                ", s3URL='" + s3URL + '\'' +
                ", serverPort=" + serverPort +
                ", applicationName='" + applicationName + '\'' +
                ", dockerContainerId=" + dockerContainerId +
                '}';
    }
}
