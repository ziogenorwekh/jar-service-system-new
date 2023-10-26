package com.jar.service.system.apporder.service.application.dto.track;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TrackAppOrderCurtResponse {

    private final String applicationName;
    private final UUID appOrderId;
    private final UUID containerId;

    @Builder
    public TrackAppOrderCurtResponse(String applicationName, UUID appOrderId, UUID containerId) {
        this.applicationName = applicationName;
        this.appOrderId = appOrderId;
        this.containerId = containerId;
    }
}
