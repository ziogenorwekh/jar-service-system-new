package com.jar.service.system.common.domain.exception;

public class GlobalInfrastructureException extends RuntimeException {
    public GlobalInfrastructureException(String message) {
        super(message);
    }

    public GlobalInfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
