package com.jar.service.system.database.service.jpa.mapper;

import com.jar.service.system.common.domain.valueobject.DatabaseId;
import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.database.service.jpa.entity.DatabaseEntity;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDataAccessMapper {

    public Database convertDatabaseEntityToDatabase(DatabaseEntity databaseEntity) {

        return Database.builder()
                .databaseUsername(databaseEntity.getDatabaseUsername())
                .databasePassword(databaseEntity.getDatabasePassword())
                .databaseName(databaseEntity.getDatabaseName())
                .userId(new UserId(databaseEntity.getUserId()))
                .accessUrl(databaseEntity.getDatabaseAccessUrl())
                .databaseId(new DatabaseId(databaseEntity.getDatabaseId()))
                .build();
    }

    public DatabaseEntity convertDatabaseToDatabaseEntity(String accessUrl, Database database) {
        return DatabaseEntity.builder()
                .databaseId(database.getId().getValue())
                .userId(database.getUserId().getValue())
                .databaseUsername(database.getDatabaseUsername())
                .databasePassword(database.getDatabasePassword())
                .databaseName(database.getDatabaseName())
                .databaseAccessUrl(accessUrl).build();
    }
}
