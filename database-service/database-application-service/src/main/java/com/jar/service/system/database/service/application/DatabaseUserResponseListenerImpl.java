package com.jar.service.system.database.service.application;

import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import com.jar.service.system.database.service.application.handler.DatabaseDeleteHandler;
import com.jar.service.system.database.service.application.ports.input.listener.DatabaseUserResponseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseUserResponseListenerImpl implements DatabaseUserResponseListener {


    private final DatabaseDeleteHandler databaseDeleteHandler;

    @Autowired
    public DatabaseUserResponseListenerImpl(DatabaseDeleteHandler databaseDeleteHandler) {
        this.databaseDeleteHandler = databaseDeleteHandler;
    }

    @Override
    public void deleteDatabase(DatabaseDeleteCommand databaseDeleteCommand) {
        databaseDeleteHandler.deleteDatabase(databaseDeleteCommand);
    }
}
