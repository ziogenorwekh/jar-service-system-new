package com.jar.service.system.apporder.service.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ServerConfig {

    private final String endPoint;
    private final String applicationName;
    private final Integer serverPort;

    private final Integer javaVersion;

    @Builder
    public ServerConfig(String endPoint, String applicationName, Integer serverPort,
                        Integer javaVersion) {
        this.endPoint = endPoint;
        this.applicationName = applicationName;
        this.serverPort = serverPort;
        this.javaVersion = javaVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfig that = (ServerConfig) o;
        return endPoint.equals(that.endPoint) && applicationName.equals(that.applicationName) && serverPort.equals(that.serverPort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endPoint, applicationName, serverPort);
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "endPoint='" + endPoint + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", serverPort=" + serverPort +
                ", javaVersion=" + javaVersion +
                '}';
    }
}
