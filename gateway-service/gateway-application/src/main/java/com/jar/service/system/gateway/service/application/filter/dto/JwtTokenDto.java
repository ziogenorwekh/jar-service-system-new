package com.jar.service.system.gateway.service.application.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class JwtTokenDto {

    private String userId;
    private String error;
    private Boolean isSuccess;
}
