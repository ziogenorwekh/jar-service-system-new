package com.jar.service.system.storage.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class StorageAppOrderNotFoundException extends ApplicationException {

    public StorageAppOrderNotFoundException(String message) {
        super(message);
    }

    public StorageAppOrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
