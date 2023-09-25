package com.jar.service.system.database.service.application.dto.delete;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DatabaseDeleteCommand {


    @NotNull(message = "User id must be necessary.")
    private final UUID userId;
}
