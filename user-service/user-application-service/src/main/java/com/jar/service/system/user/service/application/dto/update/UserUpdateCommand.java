package com.jar.service.system.user.service.application.dto.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateCommand {

    private final UUID userId;
    private final String rawPassword;
}
