package com.jar.service.system.database.service.application.handler;

import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import com.jar.service.system.database.service.application.exception.DatabaseNotFoundException;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.database.service.domain.DatabaseDomainService;
import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.common.domain.valueobject.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class DatabaseDeleteHandler {


    private final DatabaseRepository databaseRepository;


    @Autowired
    public DatabaseDeleteHandler(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Transactional
    public void deleteDatabase(DatabaseDeleteCommand databaseDeleteCommand) {
        Database database = databaseRepository.findByUserId(new UserId(databaseDeleteCommand.getUserId()))
                .orElseThrow(() -> new DatabaseNotFoundException("user not have database"));

        log.info("database delete kafka message published.");
        databaseRepository.delete(database);
    }
}
