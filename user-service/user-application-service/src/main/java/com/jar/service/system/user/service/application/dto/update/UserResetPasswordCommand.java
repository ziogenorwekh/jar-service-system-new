package com.jar.service.system.user.service.application.dto.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserResetPasswordCommand {

    @NotNull(message = "Email is not null.")
    @NotEmpty(message = "Email is not empty.")
    @Email(message = "Is must be email type.")
    private String email;

    @Builder
    public UserResetPasswordCommand(String email) {
        this.email = email;
    }
}
