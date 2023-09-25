package com.jar.service.system.database.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class DatabaseApplicationException extends ApplicationException {
    public DatabaseApplicationException(String message) {
        super(message);
    }

    public DatabaseApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
