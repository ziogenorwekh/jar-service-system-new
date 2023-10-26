package com.jar.service.system.container.service.application.dto.track;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackContainerResponse {

    private final UUID containerId;
    private final String dockerContainerId;
    private final String cpuUsage;
    private final String memoryUsage;
    private final String logs;
}
