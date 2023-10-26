package com.jar.service.system.user.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class UserDatabaseNotFoundException extends ApplicationException {
    public UserDatabaseNotFoundException(String message) {
        super(message);
    }

    public UserDatabaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
