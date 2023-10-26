package com.jar.service.system.user.service.application.dto.verify;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCodeGenerationCommand {

    @NotNull(message = "email must be not null.")
    private String email;
}
