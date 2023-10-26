package com.jar.service.system.container.service.application.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ContainerDeleteApprovalResponse {

    private final UUID appOrderId;
}
