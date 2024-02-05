package com.jar.service.system.container.service.application.dto.connect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DockerUsage {
    private final String cpuUsage;
    private final String memoryUsage;
}
