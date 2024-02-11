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

    /**
     * Create User's Database && Database User
     * @param databaseCreateCommand
     * @return
     */
    @RequestMapping(value = "/databases", method = RequestMethod.POST)
    public ResponseEntity<DatabaseCreateResponse> createDatabase(
            @RequestBody DatabaseCreateCommand databaseCreateCommand) {

        log.info("[CREATE] create database by user -> {}", databaseCreateCommand.getUserId());
        DatabaseCreateResponse databaseCreateResponse = databaseApplicationService
                .createDatabase(databaseCreateCommand);

        log.info("[CREATED] database response here access url -> {}",
                databaseCreateResponse.getAccessUrl());
        return ResponseEntity.ok().body(databaseCreateResponse);
    }

    /**
     * Track User's Database Information
     * @param userId
     * @return
     */
    @RequestMapping(value = "/databases/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> trackDatabase(@PathVariable UUID userId) {
        log.info("[TRACE] track database by user -> {}", userId);
        TrackDatabaseQuery trackDatabaseQuery = TrackDatabaseQuery.builder().userId(userId).build();
        TrackDatabaseResponse trackDatabaseResponse = databaseApplicationService
                .trackDatabase(trackDatabaseQuery);
        return ResponseEntity.ok().body(trackDatabaseResponse);
    }

    /**
     * Delete User's Database && Database User
     * @param userId
     * @return
     */
    @RequestMapping(value = "/databases/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDatabase(@PathVariable UUID userId) {

        log.info("[DELETE] delete database by user -> {}", userId);
        DatabaseDeleteCommand databaseDeleteCommand = DatabaseDeleteCommand.builder().userId(userId).build();
        databaseApplicationService.deleteDatabase(databaseDeleteCommand);
        return ResponseEntity.noContent().build();
    }
}
