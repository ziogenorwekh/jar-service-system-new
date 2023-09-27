package com.jar.service.system.database.service.application;

import com.jar.service.system.database.service.application.handler.DatabaseCreateHandler;
import com.jar.service.system.database.service.application.handler.DatabaseDeleteHandler;
import com.jar.service.system.database.service.application.handler.DatabaseTrackQueryHandler;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateCommand;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateResponse;
import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseQuery;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseApplicationServiceImpl implements DatabaseApplicationService {


    private final DatabaseCreateHandler databaseCreateHandler;
    private final DatabaseTrackQueryHandler databaseTrackQueryHandler;

    private final DatabaseDeleteHandler databaseDeleteHandler;

    @Autowired
    public DatabaseApplicationServiceImpl(DatabaseCreateHandler databaseCreateHandler,
                                          DatabaseTrackQueryHandler databaseTrackQueryHandler,
                                          DatabaseDeleteHandler databaseDeleteHandler) {
        this.databaseCreateHandler = databaseCreateHandler;
        this.databaseTrackQueryHandler = databaseTrackQueryHandler;
        this.databaseDeleteHandler = databaseDeleteHandler;
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

}
