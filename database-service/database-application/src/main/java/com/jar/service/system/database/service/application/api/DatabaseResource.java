package com.jar.service.system.database.service.application.api;


import com.jar.service.system.database.service.application.DatabaseApplicationService;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateCommand;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateResponse;
import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseQuery;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class DatabaseResource {

    private final DatabaseApplicationService databaseApplicationService;

    @Autowired
    public DatabaseResource(DatabaseApplicationService databaseApplicationService) {
        this.databaseApplicationService = databaseApplicationService;
    }

    @RequestMapping(value = "/databases", method = RequestMethod.POST)
    public ResponseEntity<DatabaseCreateResponse> createDatabase(
            @RequestBody DatabaseCreateCommand databaseCreateCommand) {

        log.info("databaseCreateCommand contents by {}", databaseCreateCommand);
        DatabaseCreateResponse databaseCreateResponse = databaseApplicationService
                .createDatabase(databaseCreateCommand);

        log.info("create database response here access url -> {}", databaseCreateResponse.getAccessUrl());
        return ResponseEntity.ok().body(databaseCreateResponse);
    }

    @RequestMapping(value = "/databases/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> trackDatabase(@PathVariable UUID userId) {

        TrackDatabaseQuery trackDatabaseQuery = TrackDatabaseQuery.builder().userId(userId).build();
        TrackDatabaseResponse trackDatabaseResponse = databaseApplicationService
                .trackDatabase(trackDatabaseQuery);
        return ResponseEntity.ok().body(trackDatabaseResponse);
    }

    @RequestMapping(value = "/databases/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDatabase(@PathVariable UUID userId) {
        DatabaseDeleteCommand databaseDeleteCommand = DatabaseDeleteCommand.builder().userId(userId).build();
        databaseApplicationService.deleteDatabase(databaseDeleteCommand);
        return ResponseEntity.noContent().build();
    }
}
