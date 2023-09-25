package com.jar.service.system.database.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.exception.DomainException;
import com.jar.service.system.common.domain.valueobject.DatabaseId;
import com.jar.service.system.common.domain.valueobject.DatabaseStatus;
import com.jar.service.system.common.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;


@Getter
public class Database extends AggregateRoot<DatabaseId> {

    private final UserId userId;
    private final String databaseName;
    private final String databaseUsername;
    private String databasePassword;

    private String accessUrl;

    private DatabaseStatus databaseStatus;

    private String errorMessage;


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
        databaseStatus = DatabaseStatus.INITIALIZED;
    }

    public void saveDatabaseRejectStatus() {
        this.databaseStatus = DatabaseStatus.DELETED;
    }

    public void saveDatabaseApprovedStatus(String accessUrl) {
        if (accessUrl == null || this.databaseStatus != DatabaseStatus.INITIALIZED) {
            throw new DomainException("don't have database access url or not initialized database.");
        }
        this.accessUrl = accessUrl;
        this.databaseStatus = DatabaseStatus.APPROVED;
    }

    public void updateNewPassword(String databaseNewPassword) {
        this.databasePassword = databaseNewPassword;
    }
}
