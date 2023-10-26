package com.jar.service.system.database.service.application.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrackDatabaseResponse {

    private final String accessUrl;
    private final String databaseName;
    private final String databaseUsername;
}
