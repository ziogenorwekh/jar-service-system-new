package com.jar.service.system.database.service.application.mapper;

import com.jar.service.system.database.service.application.dto.create.DatabaseCreateCommand;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateResponse;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseResponse;
import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDataMapper {

    public Database convertDatabaseCreateCommandToDatabase(DatabaseCreateCommand databaseCreateCommand) {
        return Database.builder()
                .userId(new UserId(databaseCreateCommand.getUserId()))
                .databaseName(databaseCreateCommand.getDatabaseName())
                .databaseUsername(databaseCreateCommand.getDatabaseUsername())
                .databasePassword(databaseCreateCommand.getDatabasePassword())
                .build();
    }

    public DatabaseCreateResponse convertDatabaseToDatabaseCreateResponse(Database database) {
        return DatabaseCreateResponse.builder()
                .accessUrl(database.getAccessUrl())
                .build();
    }

    public TrackDatabaseResponse convertDatabaseToTrackDatabaseResponse(Database database) {
        return TrackDatabaseResponse.builder()
                .accessUrl(database.getAccessUrl())
                .databaseName(database.getDatabaseName())
                .databaseUsername(database.getDatabaseUsername())
                .build();
    }

}
