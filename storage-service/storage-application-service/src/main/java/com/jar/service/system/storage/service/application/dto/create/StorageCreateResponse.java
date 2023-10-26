package com.jar.service.system.storage.service.application.dto.create;

import com.jar.service.system.common.domain.valueobject.StorageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StorageCreateResponse {

    private final String filename;
    private final String fileType;
    private final StorageStatus storageStatus;
    private final UUID appOrderId;
}
