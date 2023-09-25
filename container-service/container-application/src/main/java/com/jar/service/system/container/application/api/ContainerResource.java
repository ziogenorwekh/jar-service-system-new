package com.jar.service.system.container.application.api;


import com.jar.service.system.container.service.application.ContainerApplicationService;
import com.jar.service.system.container.service.application.dto.track.TrackContainerQuery;
import com.jar.service.system.container.service.application.dto.track.TrackContainerResponse;
import com.jar.service.system.container.service.application.dto.update.ContainerStartCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerStopCommand;
import com.jar.service.system.container.service.application.dto.update.ContainerUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ContainerResource {

    private final ContainerApplicationService containerApplicationService;

    @Autowired
    public ContainerResource(ContainerApplicationService containerApplicationService) {
        this.containerApplicationService = containerApplicationService;
    }

    @RequestMapping(value = "/containers/{containerId}", method = RequestMethod.GET)
    public ResponseEntity<TrackContainerResponse> retrieveContainer(@PathVariable UUID containerId) {
        TrackContainerResponse trackContainerResponse = containerApplicationService
                .trackQueryContainerResource(TrackContainerQuery.builder()
                .containerId(containerId).build());
        return ResponseEntity.ok(trackContainerResponse);
    }

    @RequestMapping(value = "/containers/stop/{containerId}", method = RequestMethod.PUT)
    public ResponseEntity<?> stopContainer(@PathVariable UUID containerId) {
        ContainerUpdateResponse containerUpdateResponse = containerApplicationService
                .stopContainer(ContainerStopCommand.builder()
                .containerId(containerId).build());
        return ResponseEntity.accepted().body(containerUpdateResponse);
    }

    @RequestMapping(value = "/containers/start/{containerId}", method = RequestMethod.PUT)
    public ResponseEntity<?> startContainer(@PathVariable UUID containerId) {
        ContainerUpdateResponse containerUpdateResponse = containerApplicationService
                .startContainer(ContainerStartCommand.builder()
                .containerId(containerId).build());
        return ResponseEntity.accepted().body(containerUpdateResponse);
    }

}
