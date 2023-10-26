package com.jar.service.system.apporder.service.domain.exception;

import com.jar.service.system.common.domain.exception.DomainException;

public class AppOrderDomainException extends DomainException {


    public AppOrderDomainException(String message) {
        super(message);
    }

    public AppOrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
