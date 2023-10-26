package com.jar.service.system.common.application.exceptionhandler;


import com.jar.service.system.common.application.dto.ExceptionMessageResponse;
import com.jar.service.system.common.application.dto.ExceptionValidationMessageResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;


public class GlobalExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionValidationMessageResponse handleInvalidateCommand(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();

        e.getConstraintViolations().forEach(constraintViolation ->
                errors.add(constraintViolation.getMessage()));

        return ExceptionValidationMessageResponse
                .builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(errors)
                .build();
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {



        return ExceptionMessageResponse
                .builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(String.format("Invalid Parameter input : %s",e.getValue()))
                .build();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionMessageResponse handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {

        return ExceptionMessageResponse
                .builder()
                .errorCode(HttpStatus.METHOD_NOT_ALLOWED)
                .errorMessage(e.getMessage())
                .build();
    }
}
