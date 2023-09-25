package com.jar.service.system.apporder.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class AppOrderApplicationException extends ApplicationException {

    public AppOrderApplicationException(String message) {
        super(message);
    }

    public AppOrderApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
