package com.jar.service.system.database.service.application.dto.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DatabasePwdUpdateCommand {

    private final UUID userId;
    private final String newPassword;
}
