package com.jar.service.system.apporder.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class AppOrderNotFoundException extends ApplicationException {

    public AppOrderNotFoundException(String message) {
        super(message);
    }

    public AppOrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
