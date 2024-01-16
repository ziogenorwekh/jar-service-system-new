package com.jar.service.system.apporder.service.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContainerConfig that = (ContainerConfig) o;
        return Objects.equals(s3URL, that.s3URL) && Objects.equals(applicationName, that.applicationName)
                && Objects.equals(serverPort, that.serverPort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s3URL, applicationName, serverPort);
    }
}
