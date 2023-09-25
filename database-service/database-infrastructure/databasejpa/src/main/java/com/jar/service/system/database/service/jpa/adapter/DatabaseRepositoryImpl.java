package com.jar.service.system.database.service.jpa.adapter;

import com.jar.service.system.database.service.application.exception.DatabaseSchemaException;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.database.service.jpa.entity.DatabaseEntity;
import com.jar.service.system.database.service.jpa.mapper.DatabaseDataAccessMapper;
import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.database.service.jpa.repository.DatabaseJpaRepository;
import com.jar.service.system.database.service.jpa.repository.DatabaseSchemaManagementRepository;
import com.jar.service.system.database.service.jpa.repository.SchemaKeywordJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class DatabaseRepositoryImpl implements DatabaseRepository {

    private final DatabaseJpaRepository databaseJpaRepository;
    private final DatabaseDataAccessMapper databaseDataAccessMapper;
    private final DatabaseSchemaManagementRepository databaseSchemaManagementRepository;
    private final SchemaKeywordJpaRepository schemaKeywordJpaRepository;


    @Autowired
    public DatabaseRepositoryImpl(DatabaseJpaRepository databaseJpaRepository,
                                  DatabaseDataAccessMapper databaseDataAccessMapper,
                                  DatabaseSchemaManagementRepository databaseSchemaManagementRepository,
                                  SchemaKeywordJpaRepository schemaKeywordJpaRepository) {
        this.databaseJpaRepository = databaseJpaRepository;
        this.databaseDataAccessMapper = databaseDataAccessMapper;
        this.databaseSchemaManagementRepository = databaseSchemaManagementRepository;
        this.schemaKeywordJpaRepository = schemaKeywordJpaRepository;
    }

    @Override
    public Database save(Database database) {

        schemaKeywordJpaRepository.findAll().forEach(schemaKeywordEntity -> {
            if (schemaKeywordEntity.getReservedWord().equals(database.getDatabaseName()) ||
                    schemaKeywordEntity.getReservedWord().equals(database.getDatabaseUsername())) {
                throw new DatabaseSchemaException("DatabaseName or DatabaseUsername can't registry.");
            }
        });
        databaseSchemaManagementRepository.createDatabaseAndDatabaseUser(database);
        databaseSchemaManagementRepository.grantDatabasePermission(database);

        String dbUrl = String.format("jdbc:mysql:///%s", database.getDatabaseName());
        log.info("database access URL is : {}",dbUrl);

        DatabaseEntity databaseEntity = databaseDataAccessMapper
                .convertDatabaseToDatabaseEntity(dbUrl, database);

        DatabaseEntity save = databaseJpaRepository.save(databaseEntity);

        return databaseDataAccessMapper
                .convertDatabaseEntityToDatabase(save);
    }

    @Override
    public Optional<Database> findByUserId(UserId userId) {
        return databaseJpaRepository.findDatabaseEntityByUserId(userId.getValue())
                .map(databaseDataAccessMapper::convertDatabaseEntityToDatabase);
    }


    @Override
    public void delete(Database database) {

        databaseSchemaManagementRepository.dropDatabaseAndUser(database);

        databaseJpaRepository.delete(databaseJpaRepository
                .findById(database.getId().getValue()).orElseThrow());
    }

    @Override
    public Optional<Database> findByDatabaseNameOrDatabaseUsername(String databaseUsername, String databaseName) {
        return databaseJpaRepository.
                findDatabaseEntityByDatabaseUsernameOrDatabaseName(databaseUsername, databaseName)
                .map(databaseDataAccessMapper::convertDatabaseEntityToDatabase);
    }

    @Override
    public List<Database> findAll() {
        return databaseJpaRepository.findAll().stream()
                .map(databaseDataAccessMapper::convertDatabaseEntityToDatabase).collect(Collectors.toList());
    }

}
