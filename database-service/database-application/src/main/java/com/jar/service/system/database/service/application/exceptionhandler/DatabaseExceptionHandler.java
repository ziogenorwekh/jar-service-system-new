package com.jar.service.system.database.service.application.exceptionhandler;

import com.jar.service.system.common.application.dto.ExceptionMessageResponse;
import com.jar.service.system.common.application.exceptionhandler.GlobalExceptionHandler;
import com.jar.service.system.database.service.application.exception.DatabaseApplicationException;
import com.jar.service.system.database.service.application.exception.DatabaseNotFoundException;
import com.jar.service.system.database.service.application.exception.DatabaseSchemaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DatabaseExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(value = DatabaseSchemaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleInvalidateSchemaName(DatabaseSchemaException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = DatabaseApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleDuplicatedDatabase(DatabaseApplicationException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = DatabaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessageResponse handleNotFoundDatabase(DatabaseNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .errorMessage(e.getMessage())
                .build();
    }
}
