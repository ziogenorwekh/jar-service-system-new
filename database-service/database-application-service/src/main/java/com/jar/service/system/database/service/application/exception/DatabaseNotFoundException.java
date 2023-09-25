package com.jar.service.system.database.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class DatabaseNotFoundException extends ApplicationException {
    public DatabaseNotFoundException(String message) {
        super(message);
    }

    public DatabaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
