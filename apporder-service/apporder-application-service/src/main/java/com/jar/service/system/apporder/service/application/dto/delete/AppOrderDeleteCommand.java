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

    private final UUID userId;
    private final UUID appOrderId;

    @Override
    public String toString() {
        return "AppOrderDeleteCommand{" +
                "userId=" + userId +
                ", appOrderId=" + appOrderId +
                '}';
    }
}
