package com.jar.service.system.container.service.application;

import com.jar.service.system.container.service.application.dto.track.TrackContainerLogsResponse;
import com.jar.service.system.container.service.application.dto.track.TrackContainerQuery;
import com.jar.service.system.container.service.application.dto.track.TrackContainerResponse;
import com.jar.service.system.container.service.application.dto.update.ContainerStartCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerStopCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerUpdateResponse;
import org.springframework.validation.annotation.Validated;

public interface ContainerApplicationService {

    TrackContainerResponse trackQueryContainerResource(@Validated TrackContainerQuery trackContainerQuery);

    TrackContainerLogsResponse trackQueryContainerLogs(@Validated TrackContainerQuery trackContainerQuery);

    ContainerUpdateResponse startContainer(@Validated ContainerStartCommand containerStartCommand);

    ContainerUpdateResponse stopContainer(@Validated ContainerStopCommand containerStopCommand);
}
