package com.jar.service.system.user.service.application.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserAuthenticationCommand {

    @NotNull(message = "email must be necessary.")
    private final String email;
    @NotNull(message = "password must be necessary.")
    private final String password;
}
