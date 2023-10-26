package com.jar.service.system.storage.service.application.ports.input.listener;

import com.jar.service.system.storage.service.application.dto.message.AppOrderCreatedResponse;

public interface StorageAppOrderCreatedListener {

    void saveAppOrder(AppOrderCreatedResponse appOrderCreatedResponse);
}
