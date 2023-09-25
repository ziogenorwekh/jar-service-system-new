package com.jar.service.system.database.service.application.handler;

import com.jar.service.system.database.service.application.dto.update.DatabasePwdUpdateCommand;
import com.jar.service.system.database.service.application.dto.update.DatabaseUpdatedResponse;
import com.jar.service.system.database.service.application.exception.DatabaseNotFoundException;
import com.jar.service.system.database.service.application.mapper.DatabaseDataMapper;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseUpdateHandler {

    private final DatabaseRepository databaseRepository;
    private final DatabaseDataMapper databaseDataMapper;

    @Autowired
    public DatabaseUpdateHandler(DatabaseRepository databaseRepository,
                                 DatabaseDataMapper databaseDataMapper) {
        this.databaseRepository = databaseRepository;
        this.databaseDataMapper = databaseDataMapper;
    }

    @Transactional
    public DatabaseUpdatedResponse updateNewDatabasePassword(DatabasePwdUpdateCommand
                                                                     databasePwdUpdateCommand) {
        Database database = databaseRepository.findByUserId(new UserId(databasePwdUpdateCommand.getUserId()))
                .orElseThrow(() -> new DatabaseNotFoundException(
                        String.format("database not found by userId : %s",
                                databasePwdUpdateCommand.getUserId())));
        database.updateNewPassword(databasePwdUpdateCommand.getNewPassword());
        return databaseDataMapper.convertDatabaseToDatabaseUpdatedResponse(database);
    }
}
