package com.jar.service.system.database.service.domain;

import com.jar.service.system.database.service.domain.entity.Database;

public interface DatabaseDomainService {

    void initializeDatabase(Database database);

    void createDatabase(Database database, String accessUrl);


}
