package com.jar.service.system.database.service.application;

import com.jar.service.system.database.service.application.handler.DatabaseCreateHandler;
import com.jar.service.system.database.service.application.handler.DatabaseDeleteHandler;
import com.jar.service.system.database.service.application.handler.DatabaseTrackQueryHandler;
import com.jar.service.system.database.service.domain.DatabaseDomainService;
import com.jar.service.system.database.service.application.mapper.DatabaseDataMapper;
import com.jar.service.system.database.service.application.ports.input.listener.DatabaseUserResponseListener;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDatabaseConfiguration {

    @Bean
    public DatabaseDomainService databaseDomainService() {
        return Mockito.mock(DatabaseDomainService.class);
    }

    @Bean
    public DatabaseRepository databaseRepository() {
        return Mockito.mock(DatabaseRepository.class);
    }

    @Bean
    public DatabaseUserResponseListener kafkaDatabaseDeleteEventListener() {
        return Mockito.mock(DatabaseUserResponseListener.class);
    }

    @Bean
    public DatabaseDataMapper databaseDataMapper() {
        return new DatabaseDataMapper();
    }

    @Bean
    public DatabaseCreateHandler databaseCreateHandler() {
        return new DatabaseCreateHandler(databaseDomainService(),databaseRepository(),databaseDataMapper());
    }


    @Bean
    public DatabaseTrackQueryHandler databaseTrackQueryHandler() {
        return new DatabaseTrackQueryHandler(databaseRepository(), databaseDataMapper());
    }



    @Bean
    public DatabaseDeleteHandler databaseDeleteHandler() {
        return new DatabaseDeleteHandler(databaseRepository());
    }

    @Bean
    public DatabaseApplicationService databaseApplicationService() {
        return new DatabaseApplicationServiceImpl(databaseCreateHandler(), databaseTrackQueryHandler(),
                databaseDeleteHandler());
    }
}
