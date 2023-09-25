package com.jar.service.system.container.application.exceptionhandler;

import com.jar.service.system.common.application.dto.ExceptionMessageResponse;
import com.jar.service.system.common.application.exceptionhandler.GlobalExceptionHandler;
import com.jar.service.system.container.service.application.exception.ContainerApplicationException;
import com.jar.service.system.container.service.application.exception.ContainerNotFoundException;
import com.jar.service.system.container.service.application.exception.ContainerDockerStateException;
import com.jar.service.system.container.service.domian.exception.ContainerDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ContainerExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(value = ContainerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessageResponse handleContainerNotFound(ContainerNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .errorMessage(e.getMessage())
                .build();
    }


    @ExceptionHandler(value = ContainerDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleContainerDomain(ContainerDomainException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = ContainerApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleContainerApplication(ContainerApplicationException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }


    @ExceptionHandler(value = ContainerDockerStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleContainerState(ContainerDockerStateException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }
}
