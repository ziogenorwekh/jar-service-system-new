package com.jar.service.system.storage.service.application.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AppOrderCreatedResponse {

    private final UUID appOrderId;
}
