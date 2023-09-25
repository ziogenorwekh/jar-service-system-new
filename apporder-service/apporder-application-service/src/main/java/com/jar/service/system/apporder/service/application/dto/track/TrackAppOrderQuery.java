package com.jar.service.system.apporder.service.application.dto.track;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackAppOrderQuery {

    @NotNull(message = "User must be necessary.")
    private final UUID appOrderId;
}
