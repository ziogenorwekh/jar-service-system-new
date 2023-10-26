package com.jar.service.system.container.service.domian.valueobject;

import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DockerConfig {

    private final DockerContainerId dockerContainerId;
    private final String failureMessage;

    private final String imageId;

    @Builder
    public DockerConfig(DockerContainerId dockerContainerId, String failureMessage, String imageId) {
        this.dockerContainerId = dockerContainerId;
        this.failureMessage = failureMessage;
        this.imageId = imageId;
    }
}
