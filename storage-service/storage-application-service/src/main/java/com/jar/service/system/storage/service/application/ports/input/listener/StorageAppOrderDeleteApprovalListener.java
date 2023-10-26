package com.jar.service.system.storage.service.application.ports.input.listener;

import com.jar.service.system.storage.service.application.dto.message.StorageDeleteApprovalResponse;

public interface StorageAppOrderDeleteApprovalListener {

    void deleteStorage(StorageDeleteApprovalResponse storageDeleteApprovalResponse);
}
