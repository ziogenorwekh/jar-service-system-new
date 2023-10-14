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
    @Size(min = 8, message = "Password must be at least than 8 characters.")
    @Size(max = 20, message = "Password must not exceed 20 characters.")
    private final String newPassword;
}
