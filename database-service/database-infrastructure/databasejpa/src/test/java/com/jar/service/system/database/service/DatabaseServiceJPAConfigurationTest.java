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
import com.jar.service.system.database.service.servicedb.data.JdbcDatabaseConfigData;
import com.jar.service.system.database.service.servicedb.repository.DatabaseSchemaManagementRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.jar.service.system.database.service")
@EntityScan(basePackageClasses = {DatabaseEntity.class, SchemaKeywordEntity.class})
@EnableConfigurationProperties({JdbcDatabaseConfigData.class, JpaDatabaseConfigData.class,
        DatabaseEndpointConfigData.class})
public class DatabaseServiceJPAConfigurationTest {

    private JpaDatabaseConfigData jpaDatabaseConfigData;
    private JdbcDatabaseConfigData jdbcDatabaseConfigData;
    private DatabaseEndpointConfigData testDatabaseEndpointConfigData;
    public DatabaseServiceJPAConfigurationTest(JpaDatabaseConfigData jpaDatabaseConfigData,
                                               JdbcDatabaseConfigData jdbcDatabaseConfigData,
                                               DatabaseEndpointConfigData testDatabaseEndpointConfigData) {
        this.jpaDatabaseConfigData = jpaDatabaseConfigData;
        this.jdbcDatabaseConfigData = jdbcDatabaseConfigData;
        this.testDatabaseEndpointConfigData = testDatabaseEndpointConfigData;
    }

    @Bean
    public DatabaseRepository databaseRepository(DatabaseJpaRepository databaseJpaRepository,
                                                 JdbcTemplate jdbcTemplate,
                                                 SchemaKeywordJpaRepository schemaKeywordJpaRepository) {
        return new DatabaseRepositoryImpl(databaseJpaRepository, databaseDataAccessMapper(),
                schemaManagementRepository(jdbcTemplate), schemaKeywordJpaRepository
                ,testDatabaseEndpointConfigData
        );
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

    @Primary
    @Bean(name = "JpaDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .password(jpaDatabaseConfigData.getPassword())
                .url(jpaDatabaseConfigData.getDatabaseUrl())
                .username(jpaDatabaseConfigData.getUsername())
                .driverClassName(jpaDatabaseConfigData.getDriverClassName())
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("JpaDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", jpaDatabaseConfigData.getHibernateAuto());
        properties.put("hibernate.show_sql", jpaDatabaseConfigData.getShowSql());
        return builder
                .dataSource(dataSource)
                .packages(jpaDatabaseConfigData.getPackageScanArea()) // Update with your domain package
                .persistenceUnit(jpaDatabaseConfigData.getPersistenceUnit())
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    @Bean(name = "JdbcDataSource")
    public DataSource jdbcDataSource() {
        return DataSourceBuilder.create()
                .password(jdbcDatabaseConfigData.getPassword())
                .url(jdbcDatabaseConfigData.getDatabaseUrl())
                .username(jdbcDatabaseConfigData.getUsername())
                .driverClassName(jdbcDatabaseConfigData.getDriverClassName())
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("JdbcDataSource") DataSource jdbcDataSource) {
        return new JdbcTemplate(jdbcDataSource);
    }

}
