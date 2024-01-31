package com.jar.service.system.database.service.application.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DatabaseCreateCommand {


    @NotNull(message = "Creating Database must be necessary User information.")
    private UUID userId;

    @NotNull(message = "Database name must be necessary.")
    @Pattern(regexp = "^[a-z]+$", message = "Database name must contain only lowercase English letters.")
    @Size(min = 6, max = 15, message = "Database name must be between 4 and 15 characters.")
    private final String databaseName;

    @NotNull(message = "Database Username must be necessary.")
    @Pattern(regexp = "^[a-z]+$", message = "Database username must contain " +
            "only lowercase English letters.")
    @Size(min = 4, max = 15, message = "Database username must be between 4 and 15 characters.")
    private final String databaseUsername;

    @NotNull(message = "Database Password must be necessary.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Database password must contain only lowercase English letters and number.")
    @Size(min = 8, max = 15, message = "Database password must be between 8 and 15 characters.")
    private final String databasePassword;

    @Builder
    public DatabaseCreateCommand(UUID userId, String databaseName, String databaseUsername, String databasePassword) {
        this.userId = userId;
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }
}
