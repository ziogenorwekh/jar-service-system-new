package com.jar.service.system.user.service.appilcation.exceptionhandler;

import com.jar.service.system.common.application.dto.ExceptionMessageResponse;
import com.jar.service.system.common.application.exceptionhandler.GlobalExceptionHandler;
import com.jar.service.system.common.domain.exception.ApplicationException;
import com.jar.service.system.user.service.application.exception.UserDatabaseNotFoundException;
import com.jar.service.system.user.service.application.exception.UserNotFoundException;
import com.jar.service.system.user.service.application.exception.UserUnAuthenticationException;
import com.jar.service.system.user.service.domain.exception.UserDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler extends GlobalExceptionHandler {


    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessageResponse handleUserNotFound(UserNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleMissingRequestHeader(MissingRequestHeaderException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = UserUnAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessageResponse handleUserUnAuthentication(UserUnAuthenticationException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = DisabledException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessageResponse handleUserNotActive(DisabledException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionMessageResponse handleUsernameNotFound(UsernameNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleBadCredentials(BadCredentialsException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }


    @ExceptionHandler(value = UserDatabaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessageResponse handleUserDatabaseNotFound(UserDatabaseNotFoundException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .errorMessage(e.getMessage())
                .build();
    }


    @ExceptionHandler(value = UserDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleUserDomain(UserDomainException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageResponse handleUserDomain(ApplicationException e) {
        return ExceptionMessageResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }
}
