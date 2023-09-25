package com.jar.service.system.database.service.application.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackDatabaseQuery {


    @NotNull(message = "User id must be necessary.")
    private final UUID userId;
}
