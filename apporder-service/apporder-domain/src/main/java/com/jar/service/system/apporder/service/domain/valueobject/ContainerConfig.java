package com.jar.service.system.apporder.service.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContainerConfig {

    private final String s3URL;
    private final String applicationName;

    private final Integer serverPort;

    @Builder
    public ContainerConfig(String s3URL, String applicationName, Integer serverPort) {
        this.s3URL = s3URL;
        this.applicationName = applicationName;
        this.serverPort = serverPort;
    }
}
