package com.jar.service.system.database.service.container;

import com.jar.service.system.database.service.domain.DatabaseDomainService;
import com.jar.service.system.database.service.domain.DatabaseDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DatabaseDomainService databaseDomainService() {
        return new DatabaseDomainServiceImpl();
    }
}
