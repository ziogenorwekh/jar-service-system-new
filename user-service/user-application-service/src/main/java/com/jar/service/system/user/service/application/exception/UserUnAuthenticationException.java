package com.jar.service.system.user.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class UserUnAuthenticationException extends ApplicationException {
    public UserUnAuthenticationException(String message) {
        super(message);
    }

    public UserUnAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
