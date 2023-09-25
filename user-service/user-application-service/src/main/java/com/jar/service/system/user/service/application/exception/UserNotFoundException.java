package com.jar.service.system.user.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
