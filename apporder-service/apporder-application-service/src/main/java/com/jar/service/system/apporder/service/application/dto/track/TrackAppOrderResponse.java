package com.jar.service.system.apporder.service.application.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class TrackAppOrderResponse {

    private final UUID userId;
    private final String endPoint;
    private final String applicationName;
    private final Integer serverPort;
    private final Integer javaVersion;
    private final String error;
    private final UUID containerId;



}
