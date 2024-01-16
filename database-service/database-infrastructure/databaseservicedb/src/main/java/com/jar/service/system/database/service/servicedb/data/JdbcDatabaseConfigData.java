package com.jar.service.system.database.service.servicedb.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jdbc-database-config")
public class JdbcDatabaseConfigData {
    private String driverClassName;
    private String databaseUrl;
    private String username;
    private String password;
}
