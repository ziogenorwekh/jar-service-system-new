package com.jar.service.system.database.service.jpa.repository;

import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.database.service.application.exception.DatabaseSchemaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class DatabaseSchemaManagementRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseSchemaManagementRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createDatabaseAndDatabaseUser(Database database) {
        createDatabase(database);
        integrationTryCatch(() -> {
//            String sql = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
//            jdbcTemplate.update(sql, database.getDatabaseUsername(), database.getDatabasePassword());
            jdbcTemplate.execute("CREATE USER " + database.getDatabaseUsername()
                    + "@'localhost' IDENTIFIED BY '" + database.getDatabasePassword() + "'");
        }, "Duplicated database username. OR Using Reserved Word.");
    }

    public void grantDatabasePermission(Database database) {
        integrationTryCatch(() -> {
//            String sql = "GRANT SELECT, CREATE, INSERT, UPDATE, DELETE, DROP ON ?.* TO ?@'localhost'";
//            jdbcTemplate.update(sql, database.getDatabaseName(), database.getDatabaseUsername());
            jdbcTemplate.update("GRANT SELECT, CREATE, INSERT, UPDATE, DELETE, DROP ON " +
                    database.getDatabaseName() + ".* TO " + database.getDatabaseUsername() + "@'localhost'");
        }, "Grant user error.");
    }

    public void dropDatabaseAndUser(Database database) {
        integrationTryCatch(() -> {

            jdbcTemplate.execute("DROP DATABASE " + database.getDatabaseName());
            jdbcTemplate.update("DROP USER " + database.getDatabaseUsername() + "@'localhost'");
        }, "Drop Execute database or database user.");
    }

    private void createDatabase(Database database) {
        integrationTryCatch(() -> {
//            String createDatabaseSql = "CREATE DATABASE ?";
//            jdbcTemplate.update(createDatabaseSql, database.getDatabaseName());
            jdbcTemplate.execute("CREATE DATABASE " + database.getDatabaseName());
        }, "Duplicated databaseName. OR using Reserved Word.");
    }

    private void integrationTryCatch(Runnable action, String errorMessage) {
        try {
            action.run();
        } catch (Exception e) {
            log.error("error is {}", e.getMessage());
            throw new DatabaseSchemaException(errorMessage, e);
        }
    }

}
