package com.jar.service.system.container.service.application.ports.output.dockeraccess;

import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.common.domain.valueobject.DockerStatus;
import com.jar.service.system.container.service.application.dto.connect.DockerCreateResponse;
import com.jar.service.system.container.service.application.dto.connect.DockerUsage;
import com.jar.service.system.container.service.application.dto.track.TrackContainerResponse;
import com.jar.service.system.container.service.domian.entity.Container;

public interface InstanceDockerAccess {

    DockerCreateResponse createApplicationDockerContainer(Container container);

    void deleteDockerContainer(Container container);

    DockerUsage trackContainer(Container container);

    String trackLogsContainer(DockerContainerId dockerContainerId);

    DockerStatus stopContainer(Container container);

    DockerStatus startContainer(Container container);

}
