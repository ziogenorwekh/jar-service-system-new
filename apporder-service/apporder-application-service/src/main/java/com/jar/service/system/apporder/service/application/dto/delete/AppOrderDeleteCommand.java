package com.jar.service.system.apporder.service.application.dto.delete;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AppOrderDeleteCommand {

    @NotNull(message = "User must be necessary.")
    private final UUID userId;
    @Setter
    @NotNull(message = "AppOrder must be necessary.")
    private UUID appOrderId;

    @Override
    public String toString() {
        return "AppOrderDeleteCommand{" +
                "userId=" + userId +
                ", appOrderId=" + appOrderId +
                '}';
    }
}
