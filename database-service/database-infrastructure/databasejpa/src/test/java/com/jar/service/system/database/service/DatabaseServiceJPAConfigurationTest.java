package com.jar.service.system.database.service;

import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.database.service.jpa.SchemaKeywordDataLoader;
import com.jar.service.system.database.service.jpa.adapter.DatabaseRepositoryImpl;
import com.jar.service.system.database.service.jpa.data.DatabaseEndpointConfigData;
import com.jar.service.system.database.service.jpa.data.JpaDatabaseConfigData;
import com.jar.service.system.database.service.jpa.entity.DatabaseEntity;
import com.jar.service.system.database.service.jpa.entity.SchemaKeywordEntity;
import com.jar.service.system.database.service.jpa.mapper.DatabaseDataAccessMapper;
import com.jar.service.system.database.service.jpa.repository.DatabaseJpaRepository;
import com.jar.service.system.database.service.jpa.repository.SchemaKeywordJpaRepository;
import com.jar.service.system.database.service.servicedb.repository.DatabaseSchemaManagementRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.jar.service.system.database.service")
@EntityScan(basePackageClasses = {DatabaseEntity.class, SchemaKeywordEntity.class})
public class DatabaseServiceJPAConfigurationTest {

    private DatabaseEndpointConfigData databaseEndpointConfigData;

    @Bean
    public DatabaseRepository databaseRepository(DatabaseJpaRepository databaseJpaRepository,
                                                 JdbcTemplate jdbcTemplate,
                                                 SchemaKeywordJpaRepository schemaKeywordJpaRepository) {
        return new DatabaseRepositoryImpl(databaseJpaRepository, databaseDataAccessMapper(),
                schemaManagementRepository(jdbcTemplate), schemaKeywordJpaRepository,databaseEndpointConfigData);
    }

    @Bean
    public DatabaseSchemaManagementRepository schemaManagementRepository(JdbcTemplate jdbcTemplate) {
        return new DatabaseSchemaManagementRepository(jdbcTemplate);
    }

    @Bean
    public SchemaKeywordDataLoader schemaKeywordDataLoader(SchemaKeywordJpaRepository schemaKeywordJpaRepository) {
        return new SchemaKeywordDataLoader(schemaKeywordJpaRepository);
    }

    @Bean
    public DatabaseDataAccessMapper databaseDataAccessMapper() {
        return new DatabaseDataAccessMapper();
    }

}
