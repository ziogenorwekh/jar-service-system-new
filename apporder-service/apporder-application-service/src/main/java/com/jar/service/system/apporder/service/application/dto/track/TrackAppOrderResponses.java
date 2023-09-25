package com.jar.service.system.apporder.service.application.dto.track;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TrackAppOrderResponses {

    private final String applicationName;
    private final String endPoint;
    private final UUID appOrderId;
    private final UUID containerId;

    @Builder
    public TrackAppOrderResponses(String applicationName, String endPoint, UUID appOrderId, UUID containerId) {
        this.applicationName = applicationName;
        this.endPoint = endPoint;
        this.appOrderId = appOrderId;
        this.containerId = containerId;
    }
}
