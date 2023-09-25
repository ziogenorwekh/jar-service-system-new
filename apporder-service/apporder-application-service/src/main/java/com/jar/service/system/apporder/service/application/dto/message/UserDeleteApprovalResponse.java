package com.jar.service.system.apporder.service.application.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserDeleteApprovalResponse {

    private final UUID userId;
}
