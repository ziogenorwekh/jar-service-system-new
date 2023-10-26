package com.jar.service.system.user.service.application.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackUserQuery {

    private final UUID userId;
}
