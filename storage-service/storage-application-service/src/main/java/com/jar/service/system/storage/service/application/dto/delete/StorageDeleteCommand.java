package com.jar.service.system.storage.service.application.dto.delete;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StorageDeleteCommand {

    @NotNull(message = "AppOrderId is not null.")
    private final UUID appOrderId;
}
