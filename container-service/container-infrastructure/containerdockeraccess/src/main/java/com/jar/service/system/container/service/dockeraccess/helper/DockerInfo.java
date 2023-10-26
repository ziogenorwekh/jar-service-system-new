package com.jar.service.system.container.service.dockeraccess.helper;

import com.jar.service.system.common.domain.valueobject.DockerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DockerInfo {

    private final DockerStatus dockerStatus;
    private final String dockerContainerId;
}
