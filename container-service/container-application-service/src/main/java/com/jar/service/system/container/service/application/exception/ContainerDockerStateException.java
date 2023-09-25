package com.jar.service.system.container.service.application.exception;

public class ContainerDockerStateException extends RuntimeException {
    public ContainerDockerStateException(String message) {
        super(message);
    }

    public ContainerDockerStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
