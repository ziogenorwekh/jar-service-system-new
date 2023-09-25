package com.jar.service.system.database.service.application;

import com.jar.service.system.database.service.application.handler.DatabaseCreateHandler;
import com.jar.service.system.database.service.application.handler.DatabaseDeleteHandler;
import com.jar.service.system.database.service.application.handler.DatabaseTrackQueryHandler;
import com.jar.service.system.database.service.application.handler.DatabaseUpdateHandler;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateCommand;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateResponse;
import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseQuery;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseResponse;
import com.jar.service.system.database.service.application.dto.update.DatabasePwdUpdateCommand;
import com.jar.service.system.database.service.application.dto.update.DatabaseUpdatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseApplicationServiceImpl implements DatabaseApplicationService {


    private final DatabaseCreateHandler databaseCreateHandler;
    private final DatabaseTrackQueryHandler databaseTrackQueryHandler;

    private final DatabaseDeleteHandler databaseDeleteHandler;
    private final DatabaseUpdateHandler databaseUpdateHandler;

    @Autowired
    public DatabaseApplicationServiceImpl(DatabaseCreateHandler databaseCreateHandler,
                                          DatabaseTrackQueryHandler databaseTrackQueryHandler,
                                          DatabaseDeleteHandler databaseDeleteHandler,
                                          DatabaseUpdateHandler databaseUpdateHandler) {
        this.databaseCreateHandler = databaseCreateHandler;
        this.databaseTrackQueryHandler = databaseTrackQueryHandler;
        this.databaseDeleteHandler = databaseDeleteHandler;
        this.databaseUpdateHandler = databaseUpdateHandler;
    }

    @Override
    public DatabaseCreateResponse createDatabase(DatabaseCreateCommand databaseCreateCommand) {
        return databaseCreateHandler.createDatabase(databaseCreateCommand);
    }

    @Override
    public TrackDatabaseResponse trackDatabase(TrackDatabaseQuery trackDatabaseQuery) {
        return databaseTrackQueryHandler.trackingDatabase(trackDatabaseQuery);
    }

    @Override
    public void deleteDatabase(DatabaseDeleteCommand databaseDeleteCommand) {
        databaseDeleteHandler.deleteDatabase(databaseDeleteCommand);
    }

    @Override
    public DatabaseUpdatedResponse updateDatabaseNewPassword(DatabasePwdUpdateCommand databasePwdUpdateCommand) {
        return databaseUpdateHandler.updateNewDatabasePassword(databasePwdUpdateCommand);
    }
}
