package com.jar.service.system.container.service.application.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackContainerQuery {

    @NotNull(message = "containerId is not null.")
    private final UUID containerId;
}
