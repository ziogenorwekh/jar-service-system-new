package com.jar.service.system.user.service.application.dto.update;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateCommand {

    @Setter
    private UUID userId;
    @NotEmpty(message = "CurrentPassword must be necessary.")
    private final String currentPassword;
    @NotEmpty(message = "NewPassword must be necessary.")
    @Size(min = 6, message = "NewPassword must be at least than 6 characters.")
    private final String newPassword;
}
