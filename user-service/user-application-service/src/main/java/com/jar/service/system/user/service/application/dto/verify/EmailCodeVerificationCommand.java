package com.jar.service.system.user.service.application.dto.verify;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class EmailCodeVerificationCommand {

    @NotNull(message = "userId must be not null.")
    private final UUID userId;
    @NotNull(message = "email code must be necessary.")
    private final Integer emailCode;
}
