package com.jar.service.system.container.service.application;

import com.jar.service.system.container.service.application.dto.track.TrackContainerQuery;
import com.jar.service.system.container.service.application.dto.track.TrackContainerResponse;
import com.jar.service.system.container.service.application.dto.update.ContainerStartCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerStopCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerUpdateResponse;
import com.jar.service.system.container.service.application.handler.ContainerTrackHandler;
import com.jar.service.system.container.service.application.handler.ContainerUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerApplicationServiceImpl implements ContainerApplicationService {

    private final ContainerUpdateHandler containerUpdateHandler;
    private final ContainerTrackHandler containerTrackHandler;

    @Autowired
    public ContainerApplicationServiceImpl(ContainerUpdateHandler containerUpdateHandler,
                                           ContainerTrackHandler containerTrackHandler) {
        this.containerUpdateHandler = containerUpdateHandler;
        this.containerTrackHandler = containerTrackHandler;
    }

    @Override
    public TrackContainerResponse trackQueryContainerResource(TrackContainerQuery trackContainerQuery) {
        return containerTrackHandler.inspectContainer(trackContainerQuery);
    }

    @Override
    public ContainerUpdateResponse startContainer(ContainerStartCommand containerStartCommand) {
        return containerUpdateHandler.startContainer(containerStartCommand);
    }

    @Override
    public ContainerUpdateResponse stopContainer(ContainerStopCommand containerStopCommand) {
        return containerUpdateHandler.stopContainer(containerStopCommand);
    }
}
