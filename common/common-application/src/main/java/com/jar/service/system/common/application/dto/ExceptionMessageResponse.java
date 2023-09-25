package com.jar.service.system.common.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ExceptionMessageResponse {

    private final HttpStatus errorCode;
    private final String errorMessage;
    private final ZonedDateTime errorOccurredAt;

    @Builder
    public ExceptionMessageResponse(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        errorOccurredAt = ZonedDateTime.now();
    }
}
