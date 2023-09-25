package com.jar.service.system.storage.service.application;

import com.jar.service.system.storage.service.application.dto.message.StorageDeleteApprovalResponse;
import com.jar.service.system.storage.service.application.handler.StorageDeleteHandler;
import com.jar.service.system.storage.service.application.ports.input.listener.StorageAppOrderDeleteApprovalListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageAppOrderDeleteApprovalListenerImpl implements StorageAppOrderDeleteApprovalListener {

    private final StorageDeleteHandler storageDeleteHandler;

    @Autowired
    public StorageAppOrderDeleteApprovalListenerImpl(StorageDeleteHandler storageDeleteHandler) {
        this.storageDeleteHandler = storageDeleteHandler;
    }

    @Override
    public void deleteStorage(StorageDeleteApprovalResponse storageDeleteApprovalResponse) {
        storageDeleteHandler.deleteStorageByMessage(storageDeleteApprovalResponse);
    }
}
