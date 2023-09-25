package com.jar.service.system.container.service.domian;

import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.container.service.domian.entity.Container;
import com.jar.service.system.container.service.domian.event.ContainerCreatedEvent;
import com.jar.service.system.container.service.domian.event.ContainerRejectedEvent;
import com.jar.service.system.container.service.domian.valueobject.DockerConfig;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class ContainerDomainServiceImpl implements ContainerDomainService {


    @Override
    public ContainerCreatedEvent successCreateContainer(Container container,
                                                        DockerConfig dockerContainerId) {
        container.successfulContainerizing(dockerContainerId);
        return new ContainerCreatedEvent(container, ZonedDateTime.now());
    }

    @Override
    public ContainerRejectedEvent failureCreateContainer(Container container, String error) {
        container.rejectedContainerizing(error);
        return new ContainerRejectedEvent(container, ZonedDateTime.now());
    }

    @Override
    public void startContainer(Container container, ContainerStatus containerStatus) {
        container.startContainer(containerStatus);
    }

    @Override
    public void stopContainer(Container container,ContainerStatus containerStatus) {
        container.stopContainer(containerStatus);
    }


}
