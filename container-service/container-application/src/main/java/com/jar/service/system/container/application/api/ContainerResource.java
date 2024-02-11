package com.jar.service.system.container.application.api;


import com.jar.service.system.container.service.application.ContainerApplicationService;
import com.jar.service.system.container.service.application.dto.track.TrackContainerLogsResponse;
import com.jar.service.system.container.service.application.dto.track.TrackContainerQuery;
import com.jar.service.system.container.service.application.dto.track.TrackContainerResponse;
import com.jar.service.system.container.service.application.dto.update.ContainerStartCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerStopCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerUpdateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api")
public class ContainerResource {

    private final ContainerApplicationService containerApplicationService;

    @Autowired
    public ContainerResource(ContainerApplicationService containerApplicationService) {
        this.containerApplicationService = containerApplicationService;
    }

    /**
     * Track Service's Information, which User's Application in Docker Container
     * @param containerId
     * @return
     */
    @RequestMapping(value = "/containers/{containerId}", method = RequestMethod.GET)
    public ResponseEntity<TrackContainerResponse> trackContainer(@PathVariable UUID containerId) {

        log.info("[TRACE] track {} Container", containerId);
        TrackContainerResponse trackContainerResponse = containerApplicationService
                .trackQueryContainerResource(TrackContainerQuery.builder()
                        .containerId(containerId).build());
        return ResponseEntity.ok(trackContainerResponse);
    }

    /**
     * Tracking User's Application in Docker Container
     * @param containerId
     * @return
     */
    @RequestMapping(value = "/containers/logs/{containerId}", method = RequestMethod.GET)
    public ResponseEntity<TrackContainerLogsResponse> trackLogsContainer(@PathVariable UUID containerId) {

        log.info("[TRACE] track logs {} Container", containerId);
        TrackContainerLogsResponse trackContainerLogsResponse = containerApplicationService.trackQueryContainerLogs(TrackContainerQuery.builder()
                .containerId(containerId).build());
        return ResponseEntity.accepted().body(trackContainerLogsResponse);
    }

    /**
     * Stop User's Application in Docker Container
     * @param containerId
     * @return
     */
    @RequestMapping(value = "/containers/stop/{containerId}", method = RequestMethod.PUT)
    public ResponseEntity<ContainerUpdateResponse> stopContainer(@PathVariable UUID containerId) {

        log.info("[UPDATE] update stop {} Container", containerId);
        ContainerUpdateResponse containerUpdateResponse = containerApplicationService
                .stopContainer(ContainerStopCommand.builder()
                .containerId(containerId).build());
        return ResponseEntity.accepted().body(containerUpdateResponse);
    }

    /**
     * Start User's Application in Docker Container
     * @param containerId
     * @return
     */
    @RequestMapping(value = "/containers/start/{containerId}", method = RequestMethod.PUT)
    public ResponseEntity<ContainerUpdateResponse> startContainer(@PathVariable UUID containerId) {

        log.info("[UPDATE] update start {} Container", containerId);
        ContainerUpdateResponse containerUpdateResponse = containerApplicationService
                .startContainer(ContainerStartCommand.builder()
                .containerId(containerId).build());
        return ResponseEntity.accepted().body(containerUpdateResponse);
    }

}
