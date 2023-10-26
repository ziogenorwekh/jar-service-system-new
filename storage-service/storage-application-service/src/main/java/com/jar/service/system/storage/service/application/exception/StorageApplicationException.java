package com.jar.service.system.storage.service.application.exception;

import com.jar.service.system.common.domain.exception.ApplicationException;

public class StorageApplicationException extends ApplicationException {
    public StorageApplicationException(String message) {
        super(message);
    }

    public StorageApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
