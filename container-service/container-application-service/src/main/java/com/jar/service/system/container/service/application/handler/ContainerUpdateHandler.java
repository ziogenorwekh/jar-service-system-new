package com.jar.service.system.container.service.application.handler;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerStatus;
import com.jar.service.system.container.service.application.dto.update.ContainerStartCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerStopCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerUpdateResponse;
import com.jar.service.system.container.service.application.exception.ContainerApplicationException;
import com.jar.service.system.container.service.application.exception.ContainerNotFoundException;
import com.jar.service.system.container.service.application.mapper.ContainerDataMapper;
import com.jar.service.system.container.service.application.ports.output.dockeraccess.InstanceDockerAccess;
import com.jar.service.system.container.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.container.service.domian.ContainerDomainService;
import com.jar.service.system.container.service.domian.entity.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class ContainerUpdateHandler {

    private final InstanceDockerAccess instanceDockerAccess;
    private final ContainerRepository containerRepository;
    private final ContainerDataMapper containerDataMapper;
    private final ContainerDomainService containerDomainService;

    @Autowired
    public ContainerUpdateHandler(InstanceDockerAccess instanceDockerAccess,
                                  ContainerRepository containerRepository,
                                  ContainerDataMapper containerDataMapper,
                                  ContainerDomainService containerDomainService) {
        this.instanceDockerAccess = instanceDockerAccess;
        this.containerRepository = containerRepository;
        this.containerDataMapper = containerDataMapper;
        this.containerDomainService = containerDomainService;
    }

    @Transactional
    public ContainerUpdateResponse startContainer(ContainerStartCommand containerStartCommand) {
        Container container = findContainer(containerStartCommand.getContainerId());
        DockerStatus dockerStatus = instanceDockerAccess.startContainer(container);
        switch (dockerStatus) {
            case STARTING, RUNNING -> {
                containerDomainService.startContainer(container,ContainerStatus.STARTED);
                Container saved = containerRepository.save(container);
                return containerDataMapper.convertContainerUpdateResponse(saved);
            }
        }
        throw new ContainerApplicationException("Container is not started.");
    }

    @Transactional
    public ContainerUpdateResponse stopContainer(ContainerStopCommand containerStopCommand) {
        Container container = findContainer(containerStopCommand.getContainerId());
        DockerStatus dockerStatus = instanceDockerAccess.stopContainer(container);
        switch (dockerStatus) {
            case PAUSED, EXITED -> {
                containerDomainService.stopContainer(container, ContainerStatus.STOPPED);
                Container saved = containerRepository.save(container);
                return containerDataMapper.convertContainerUpdateResponse(saved);
            }
        }
        throw new ContainerApplicationException("Container is not stopped.");
    }


    private Container findContainer(UUID containerId) {
        return containerRepository.findByContainerId(new ContainerId(containerId))
                .orElseThrow(() -> new ContainerNotFoundException(String.format("Container is not found by id : %s",
                        containerId)));
    }
}
