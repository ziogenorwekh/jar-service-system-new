package com.jar.service.system.database.service.application.handler;

import com.jar.service.system.database.service.application.dto.track.TrackDatabaseQuery;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseResponse;
import com.jar.service.system.database.service.application.exception.DatabaseNotFoundException;
import com.jar.service.system.database.service.application.mapper.DatabaseDataMapper;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseTrackQueryHandler {


    private final DatabaseRepository databaseRepository;
    private final DatabaseDataMapper databaseDataMapper;


    public DatabaseTrackQueryHandler(DatabaseRepository databaseRepository,
                                     DatabaseDataMapper databaseDataMapper) {
        this.databaseRepository = databaseRepository;
        this.databaseDataMapper = databaseDataMapper;
    }

    @Transactional(readOnly = true)
    public TrackDatabaseResponse trackingDatabase(TrackDatabaseQuery trackDatabaseQuery) {
        Database database = databaseRepository.findByUserId(new UserId(trackDatabaseQuery.getUserId()))
                .orElseThrow(() ->
                        new DatabaseNotFoundException(
                                String.format("database is not found by user id : %s",
                                        trackDatabaseQuery.getUserId())));

        return databaseDataMapper.convertDatabaseToTrackDatabaseResponse(database);
    }
}
