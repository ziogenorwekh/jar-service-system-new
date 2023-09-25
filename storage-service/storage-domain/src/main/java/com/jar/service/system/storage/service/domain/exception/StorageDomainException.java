package com.jar.service.system.storage.service.domain.exception;

import com.jar.service.system.common.domain.exception.DomainException;

public class StorageDomainException extends DomainException {

    public StorageDomainException(String message) {
        super(message);
    }

    public StorageDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
