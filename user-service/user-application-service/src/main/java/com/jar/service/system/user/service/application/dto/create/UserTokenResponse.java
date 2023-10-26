package com.jar.service.system.user.service.application.dto.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserTokenResponse {

    private final UUID userId;
    private final String accessToken;
}
