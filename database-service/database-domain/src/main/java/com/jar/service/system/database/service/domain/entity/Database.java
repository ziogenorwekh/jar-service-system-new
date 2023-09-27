package com.jar.service.system.database.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.exception.DomainException;
import com.jar.service.system.common.domain.valueobject.DatabaseId;
import com.jar.service.system.common.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
public class Database extends AggregateRoot<DatabaseId> {

    private final UserId userId;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    private String accessUrl;

    @Builder
    private Database(DatabaseId databaseId,
                     UserId userId,
                     String databaseName,
                     String databaseUsername,
                     String databasePassword,
                     String accessUrl) {
        super.setId(databaseId);
        this.userId = userId;
        this.databaseName = databaseName;
        this.accessUrl = accessUrl;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }


    public void initializeDatabase() {
        super.setId(new DatabaseId(UUID.randomUUID()));
    }

    public void saveDatabase(String accessUrl) {
        if (accessUrl == null) {
            throw new DomainException("access url is null.");
        }
        this.accessUrl = accessUrl;
    }

}
