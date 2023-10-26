package com.jar.service.system.database.service.application;

import com.jar.service.system.database.service.application.dto.create.DatabaseCreateCommand;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateResponse;
import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseQuery;
import com.jar.service.system.database.service.application.dto.track.TrackDatabaseResponse;
import org.springframework.validation.annotation.Validated;

public interface DatabaseApplicationService {


    DatabaseCreateResponse createDatabase(@Validated DatabaseCreateCommand databaseCreateCommand);

    TrackDatabaseResponse trackDatabase(@Validated TrackDatabaseQuery trackDatabaseQuery);

    void deleteDatabase(@Validated DatabaseDeleteCommand databaseDeleteCommand);

}
