package com.jar.service.system.user.service.application.dto.delete;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserDeleteCommand {

    @NotNull
    private final UUID userId;
}
