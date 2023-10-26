package com.jar.service.system.container.service.domian;

import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.container.service.domian.entity.Container;
import com.jar.service.system.container.service.domian.event.ContainerCreatedEvent;
import com.jar.service.system.container.service.domian.event.ContainerRejectedEvent;
import com.jar.service.system.container.service.domian.valueobject.DockerConfig;

public interface ContainerDomainService {

    ContainerCreatedEvent successCreateContainer(Container container, DockerConfig dockerConfig);

    ContainerRejectedEvent failureCreateContainer(Container container, String error);

    void startContainer(Container container, ContainerStatus containerStatus);

    void stopContainer(Container container,ContainerStatus containerStatus);

}
