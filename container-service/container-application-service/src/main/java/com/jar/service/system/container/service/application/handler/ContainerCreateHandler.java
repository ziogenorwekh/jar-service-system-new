package com.jar.service.system.container.service.application.handler;

import com.jar.service.system.container.service.application.dto.connect.DockerCreateResponse;
import com.jar.service.system.container.service.application.dto.message.ContainerCreationApprovalResponse;
import com.jar.service.system.container.service.application.mapper.ContainerDataMapper;
import com.jar.service.system.container.service.application.ports.output.dockeraccess.InstanceDockerAccess;
import com.jar.service.system.container.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.container.service.domian.ContainerDomainService;
import com.jar.service.system.container.service.domian.entity.Container;
import com.jar.service.system.container.service.domian.event.ContainerCreatedEvent;
import com.jar.service.system.container.service.domian.event.ContainerEvent;
import com.jar.service.system.container.service.domian.event.ContainerRejectedEvent;
import com.jar.service.system.container.service.domian.valueobject.DockerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ContainerCreateHandler {


    private final ContainerRepository containerRepository;
    private final ContainerDomainService containerDomainService;
    private final InstanceDockerAccess instanceDockerAccess;
    private final ContainerDataMapper containerDataMapper;

    @Autowired
    public ContainerCreateHandler(ContainerRepository containerRepository,
                                  ContainerDomainService containerDomainService,
                                  InstanceDockerAccess instanceDockerAccess,
                                  ContainerDataMapper containerDataMapper) {
        this.containerRepository = containerRepository;
        this.containerDomainService = containerDomainService;
        this.instanceDockerAccess = instanceDockerAccess;
        this.containerDataMapper = containerDataMapper;
    }

    @Transactional
    public ContainerEvent saveContainer(ContainerCreationApprovalResponse containerCreationApprovalResponse) {
        Container container = containerDataMapper.
                convertContainerApprovalResponseToContainer(containerCreationApprovalResponse);
        Optional<Container> optionalContainer = containerRepository.
                findByApplicationNameOrDockerContainerId(container);
        if (optionalContainer.isPresent()) {
            return rejectedEvent(container, "Already container Exists.");
        }

        DockerCreateResponse dockerCreateResponse = instanceDockerAccess
                .createApplicationDockerContainer(container);

        DockerConfig dockerConfig = containerDataMapper
                .convertDockerResponseToDockerConfig(dockerCreateResponse);

        switch (dockerCreateResponse.getDockerStatus()) {
            case RUNNING, STARTING -> {
                return createdEvent(container, dockerConfig);
            }
            case DEAD, EXITED, PAUSED, UNKNOWN_ERROR -> {
                return rejectedEvent(container, dockerConfig.getFailureMessage());
            }
            default -> {
                return rejectedEvent(container, "Unknown Container State Error.");
            }
        }
    }

    private ContainerCreatedEvent createdEvent(Container container, DockerConfig dockerConfig) {
        ContainerCreatedEvent createdEvent = containerDomainService.successCreateContainer(container,
                dockerConfig);
        containerRepository.save(container);
        return createdEvent;
    }

    private ContainerRejectedEvent rejectedEvent(Container container, String error) {
        ContainerRejectedEvent rejectedEvent = containerDomainService
                .failureCreateContainer(container, error);
        return rejectedEvent;
    }
}
