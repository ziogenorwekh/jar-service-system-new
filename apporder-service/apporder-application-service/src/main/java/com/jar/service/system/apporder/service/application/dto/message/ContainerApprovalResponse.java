package com.jar.service.system.apporder.service.application.dto.message;

import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ContainerApprovalResponse {

    private final UUID containerId;
    private final UUID appOrderId;
    private final ContainerStatus containerStatus;
    private final String applicationName;
    private final String error;
    private final String dockerContainerId;
    private final Integer serverPort;
    private final Integer javaVersion;
}
