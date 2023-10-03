package com.jar.service.system.apporder.service.application.exceptionhandler;

import com.jar.service.system.apporder.service.application.exception.AppOrderApplicationException;
import com.jar.service.system.apporder.service.application.exception.AppOrderNotFoundException;
import com.jar.service.system.apporder.service.application.exception.AppOrderNotOwnerException;
import com.jar.service.system.common.application.dto.ExceptionMessageResponse;
import com.jar.service.system.common.application.exceptionhandler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AppOrderExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(value = AppOrderApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleAppOrderApplication(AppOrderApplicationException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleMissingHeader(MissingRequestHeaderException e) {

        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(String.format("%s header is necessary.",e.getHeaderName()))
                .build();
    }

    @ExceptionHandler(value = AppOrderNotOwnerException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessageResponse handleNotOwner(AppOrderNotOwnerException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = AppOrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessageResponse handleAppOrderNotFound(AppOrderNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .errorMessage(e.getMessage())
                .build();
    }
}
