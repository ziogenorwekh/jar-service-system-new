package com.jar.service.system.database.service.application.ports.output.repository;

import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DatabaseRepository {


    Database save(Database database);


    Optional<Database> findByUserId(UserId userId);

    void delete(Database database);

    Optional<Database> findByDatabaseNameOrDatabaseUsername(String databaseUsername, String databaseName);

    List<Database> findAll();


}
