package com.jar.service.system.database.service.domain;

import com.jar.service.system.database.service.domain.entity.Database;

import java.time.ZonedDateTime;


public class DatabaseDomainServiceImpl implements DatabaseDomainService {
    @Override
    public void initializeDatabase(Database database) {
        database.initializeDatabase();
    }

    @Override
    public void createDatabase(Database database, String accessUrl) {
        database.saveDatabaseApprovedStatus(accessUrl);
    }
}
