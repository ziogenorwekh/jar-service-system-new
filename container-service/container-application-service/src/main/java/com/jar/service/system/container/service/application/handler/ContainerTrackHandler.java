package com.jar.service.system.container.service.application.handler;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.container.service.application.dto.connect.DockerUsage;
import com.jar.service.system.container.service.application.dto.track.TrackContainerLogsResponse;
import com.jar.service.system.container.service.application.dto.track.TrackContainerQuery;
import com.jar.service.system.container.service.application.dto.track.TrackContainerResponse;
import com.jar.service.system.container.service.application.exception.ContainerDockerStateException;
import com.jar.service.system.container.service.application.exception.ContainerNotFoundException;
import com.jar.service.system.container.service.application.mapper.ContainerDataMapper;
import com.jar.service.system.container.service.application.ports.output.dockeraccess.InstanceDockerAccess;
import com.jar.service.system.container.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.container.service.domian.entity.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ContainerTrackHandler {

    private final InstanceDockerAccess instanceDockerAccess;
    private final ContainerRepository containerRepository;
    private final ContainerDataMapper containerDataMapper;

    @Autowired
    public ContainerTrackHandler(InstanceDockerAccess instanceDockerAccess,
                                 ContainerRepository containerRepository,
                                 ContainerDataMapper containerDataMapper) {
        this.instanceDockerAccess = instanceDockerAccess;
        this.containerRepository = containerRepository;
        this.containerDataMapper = containerDataMapper;
    }


    @Transactional(readOnly = true)
    public TrackContainerResponse inspectContainer(TrackContainerQuery trackContainerQuery) {
        Container container = findContainer(trackContainerQuery);
        DockerUsage dockerUsage = instanceDockerAccess.trackContainer(container.getDockerContainerId().getValue());

        return containerDataMapper.convertDockerUsageTrackContainerResponse(dockerUsage, container);
    }

    @Transactional(readOnly = true)
    public TrackContainerLogsResponse trackContainerLogs(TrackContainerQuery trackContainerQuery) {
        Container container = findContainer(trackContainerQuery);
        String logs = instanceDockerAccess.trackLogsContainer(container.getDockerContainerId());
        return TrackContainerLogsResponse.builder().logs(logs).build();
    }


    private Container findContainer(TrackContainerQuery trackContainerQuery) {
        Container container = containerRepository.findByContainerId(new ContainerId(trackContainerQuery.getContainerId()))
                .orElseThrow(() -> new ContainerNotFoundException(String.
                        format("Container is not found by containerId : %s", trackContainerQuery.getContainerId())));

        if (container.getContainerStatus() != ContainerStatus.STARTED) {
            throw new ContainerDockerStateException("Container must be started.");
        }
        return container;
    }



}
