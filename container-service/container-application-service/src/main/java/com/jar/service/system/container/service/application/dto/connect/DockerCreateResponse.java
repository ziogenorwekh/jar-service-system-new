package com.jar.service.system.container.service.application.dto.connect;

import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.common.domain.valueobject.DockerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DockerCreateResponse {


    private final DockerContainerId dockerContainerId;
    private final DockerStatus dockerStatus;
    private final String error;
    private final String image;
}
