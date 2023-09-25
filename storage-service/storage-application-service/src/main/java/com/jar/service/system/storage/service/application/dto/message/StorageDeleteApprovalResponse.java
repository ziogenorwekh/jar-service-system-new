package com.jar.service.system.storage.service.application.dto.message;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.StorageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StorageDeleteApprovalResponse {

    private final UUID appOrderId;
}
