package com.jar.service.system.user.service.application.dto.track;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrackUserResponse {

    private final String username;
    private final String email;
}
