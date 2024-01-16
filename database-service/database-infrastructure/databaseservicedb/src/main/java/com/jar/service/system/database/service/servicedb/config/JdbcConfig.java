package com.jar.service.system.database.service.servicedb.config;

import com.jar.service.system.database.service.servicedb.data.JdbcDatabaseConfigData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    private final JdbcDatabaseConfigData jdbcDatabaseConfigData;

    public JdbcConfig(JdbcDatabaseConfigData jdbcDatabaseConfigData) {
        this.jdbcDatabaseConfigData = jdbcDatabaseConfigData;
    }

    @Bean(name = "JdbcDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .password(jdbcDatabaseConfigData.getPassword())
                .url(jdbcDatabaseConfigData.getDatabaseUrl())
                .username(jdbcDatabaseConfigData.getUsername())
                .driverClassName(jdbcDatabaseConfigData.getDriverClassName())
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("JdbcDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
