package com.jar.service.system.storage.service.application;

import com.jar.service.system.storage.service.application.dto.message.AppOrderCreatedResponse;
import com.jar.service.system.storage.service.application.handler.StorageCreateHandler;
import com.jar.service.system.storage.service.application.ports.input.listener.StorageAppOrderCreatedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageAppOrderCreatedListenerImpl implements StorageAppOrderCreatedListener {

    private final StorageCreateHandler storageCreateHandler;

    @Autowired
    public StorageAppOrderCreatedListenerImpl(StorageCreateHandler storageCreateHandler) {
        this.storageCreateHandler = storageCreateHandler;
    }

    @Override
    public void saveAppOrder(AppOrderCreatedResponse appOrderCreatedResponse) {
        storageCreateHandler.saveAppOrder(appOrderCreatedResponse);
    }
}
