package com.jar.service.system.apporder.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class AppOrderNotOwnerException extends ApplicationException {

    public AppOrderNotOwnerException(String message) {
        super(message);
    }

    public AppOrderNotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
