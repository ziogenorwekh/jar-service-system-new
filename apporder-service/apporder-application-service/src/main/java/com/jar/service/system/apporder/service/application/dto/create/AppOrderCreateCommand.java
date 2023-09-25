package com.jar.service.system.apporder.service.application.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AppOrderCreateCommand {

    @NotNull(message = "User must be necessary.")
    private final UUID userId;
    @NotNull(message = "The application name must be specified.")
    @Min(value = 4, message = "Application name must be less than 4.")
    private final String applicationName;
    @NotNull(message = "The server port must be necessary.")
    @Min(value = 10000, message = "Server port only can use between 10000 and 50000.")
    private final Integer serverPort;
    @NotNull(message = "The server java version must be necessary.")
    private final Integer javaVersion;
}
