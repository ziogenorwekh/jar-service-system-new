package com.jar.service.system.database.service.domain.exception;

import com.jar.service.system.common.domain.exception.DomainException;

public class DatabaseDomainException extends DomainException {

    public DatabaseDomainException(String message) {
        super(message);
    }

    public DatabaseDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
