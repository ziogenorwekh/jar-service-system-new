package com.jar.service.system.database.service.application.ports.input.listener;


import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;

public interface DatabaseUserResponseListener {


    void deleteDatabase(DatabaseDeleteCommand databaseDeleteCommand);
}
