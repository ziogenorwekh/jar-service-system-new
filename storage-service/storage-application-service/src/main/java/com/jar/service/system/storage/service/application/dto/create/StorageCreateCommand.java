package com.jar.service.system.storage.service.application.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StorageCreateCommand {

    private final MultipartFile multipartFile;
    private final UUID appOrderId;
    private final UUID userId;
}
