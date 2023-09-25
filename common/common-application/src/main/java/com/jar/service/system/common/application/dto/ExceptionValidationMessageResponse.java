package com.jar.service.system.common.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class ExceptionValidationMessageResponse {
    private final HttpStatus errorCode;
    private final List<String> errorMessage;
    private final ZonedDateTime errorOccurredAt;

    @Builder
    public ExceptionValidationMessageResponse(HttpStatus errorCode, List<String> errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        errorOccurredAt = ZonedDateTime.now();
    }
}
