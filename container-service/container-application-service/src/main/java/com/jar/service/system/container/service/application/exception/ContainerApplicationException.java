package com.jar.service.system.container.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class ContainerApplicationException extends ApplicationException {
    public ContainerApplicationException(String message) {
        super(message);
    }

    public ContainerApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
