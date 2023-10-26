package com.jar.service.system.storage.application.exceptionhandler;

import com.jar.service.system.common.application.dto.ExceptionMessageResponse;
import com.jar.service.system.common.application.exceptionhandler.GlobalExceptionHandler;
import com.jar.service.system.storage.service.application.exception.StorageAmazonS3Exception;
import com.jar.service.system.storage.service.application.exception.StorageAppOrderNotFoundException;
import com.jar.service.system.storage.service.application.exception.StorageApplicationException;
import com.jar.service.system.storage.service.application.exception.StorageNotFoundException;
import com.jar.service.system.storage.service.domain.exception.StorageDomainException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StorageExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(value = StorageApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleStorageApplication(StorageApplicationException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleMissingRequestHeader(MissingRequestHeaderException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(String.format("%s header is necessary.",e.getHeaderName()))
                .build();
    }

    @ExceptionHandler(value = SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleMaxFileSize(SizeLimitExceededException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage("File must not exceed 100MB.")
                .build();
    }

    @ExceptionHandler(value = StorageAppOrderNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleNotFoundAppOrder(StorageAppOrderNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = StorageAmazonS3Exception.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionMessageResponse handleAmazonS3(StorageAmazonS3Exception e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = StorageDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleStorageDomain(StorageDomainException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = StorageNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleStorageNotfound(StorageNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }
}
