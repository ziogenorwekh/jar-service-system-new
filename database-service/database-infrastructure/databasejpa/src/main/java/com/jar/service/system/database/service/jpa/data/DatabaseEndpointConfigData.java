package com.jar.service.system.database.service.jpa.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "database-endpoint")
public class DatabaseEndpointConfigData {

    private String endpointUrl;
}
