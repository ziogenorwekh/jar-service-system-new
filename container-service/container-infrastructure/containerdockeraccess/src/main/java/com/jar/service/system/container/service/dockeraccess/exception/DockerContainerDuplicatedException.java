package com.jar.service.system.container.service.dockeraccess.exception;

public class DockerContainerDuplicatedException extends RuntimeException {
    public DockerContainerDuplicatedException(String message) {
        super(message);
    }

    public DockerContainerDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
