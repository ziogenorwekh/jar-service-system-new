package com.jar.service.system.database.service.application.handler;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateCommand;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateResponse;
import com.jar.service.system.database.service.application.exception.DatabaseApplicationException;
import com.jar.service.system.database.service.application.mapper.DatabaseDataMapper;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.database.service.domain.DatabaseDomainService;
import com.jar.service.system.database.service.domain.entity.Database;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class DatabaseCreateHandler {

    private final DatabaseDomainService databaseDomainService;
    private final DatabaseRepository databaseRepository;
    private final DatabaseDataMapper databaseDataMapper;

    @Value("${database.root-user}")
    private String rootUser;

    @Value("${database.rds-user}")
    private String rdsUser;


    @Autowired
    public DatabaseCreateHandler(DatabaseDomainService databaseDomainService,
                                 DatabaseRepository databaseRepository,
                                 DatabaseDataMapper databaseDataMapper) {
        this.databaseDomainService = databaseDomainService;
        this.databaseRepository = databaseRepository;
        this.databaseDataMapper = databaseDataMapper;
    }


    @Transactional
    public DatabaseCreateResponse createDatabase(DatabaseCreateCommand databaseCreateCommand) {
        preventDefaultDatabaseUser(databaseCreateCommand);
        validateDuplicatedDatabase(databaseCreateCommand);
        validateDuplicateCreateDatabaseByUser(databaseCreateCommand);
        Database database = databaseDataMapper.convertDatabaseCreateCommandToDatabase(databaseCreateCommand);
        databaseDomainService.initializeDatabase(database);
        return databaseCreateHelper(database);
    }

    private DatabaseCreateResponse databaseCreateHelper(Database database) {
        Database saved = databaseRepository.save(database);
        databaseDomainService.createDatabase(database, saved.getAccessUrl());
        log.info("database created by id : {} and kafka message published.", saved.getId());
        return databaseDataMapper.convertDatabaseToDatabaseCreateResponse(saved);
    }

    private void validateDuplicatedDatabase(DatabaseCreateCommand databaseCreateCommand) {
        Optional<Database> database = databaseRepository
                .findByDatabaseNameOrDatabaseUsername(databaseCreateCommand.getDatabaseUsername(),
                        databaseCreateCommand.getDatabaseName());
        if (database.isPresent()) {
            throw new DatabaseApplicationException("database name or username is already exist.");
        }
    }

    private void validateDuplicateCreateDatabaseByUser(DatabaseCreateCommand databaseCreateCommand) {
        Optional<Database> databaseOptional = databaseRepository
                .findByUserId(new UserId(databaseCreateCommand.getUserId()));
        if (databaseOptional.isPresent()) {
            throw new DatabaseApplicationException(String.format("%s user has already database.",
                    databaseCreateCommand.getUserId()));
        }
    }

    private void preventDefaultDatabaseUser(DatabaseCreateCommand databaseCreateCommand) {
        if (databaseCreateCommand.getDatabaseUsername().equals(rdsUser)) {
            throw new DatabaseApplicationException("this user already exists.");
        }
        if (databaseCreateCommand.getDatabaseUsername().equals(rootUser)) {
            throw new DatabaseApplicationException("'root' username not use.");
        }
    }
}
