package com.jar.service.system.database.service.application.dto.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DatabaseUpdatedResponse {

    private final String accessUrl;
    private final String newPassword;
}
