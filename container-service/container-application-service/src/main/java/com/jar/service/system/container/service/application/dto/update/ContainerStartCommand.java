package com.jar.service.system.container.service.application.dto.update;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class ContainerStartCommand {


    @NotNull(message = "appOrderId is not null.")
    private final UUID containerId;
}
