package com.jar.service.system.container.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class ContainerNotFoundException extends ApplicationException {
    public ContainerNotFoundException(String message) {
        super(message);
    }

    public ContainerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
