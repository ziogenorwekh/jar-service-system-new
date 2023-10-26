package com.jar.service.system.container.service.domian.exception;

import com.jar.service.system.common.domain.exception.DomainException;

public class ContainerDomainException extends DomainException {

    public ContainerDomainException(String message) {
        super(message);
    }

    public ContainerDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
