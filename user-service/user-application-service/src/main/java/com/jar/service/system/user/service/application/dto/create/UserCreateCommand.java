package com.jar.service.system.user.service.application.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserCreateCommand {

    @NotEmpty(message = "Email must be necessary.")
    @Email
    private String email;

    @NotEmpty(message = "Username must be necessary.")
    @Size(min = 4, message = "username must be at least than 4 characters.")
    private String username;


    @NotEmpty(message = "Password must be necessary.")
    @Size(min = 6, message = "Password must be at least than 6 characters.")
    private String password;
}
