package com.jar.service.system.storage.service.domain;

import com.jar.service.system.storage.service.domain.entity.Storage;
import com.jar.service.system.storage.service.domain.event.StorageCreatedEvent;
import com.jar.service.system.storage.service.domain.event.StorageRejectedEvent;
import com.jar.service.system.storage.service.domain.valueobject.StorageInfo;

import java.time.ZonedDateTime;


public class StorageDomainServiceImpl implements StorageDomainService {

    @Override
    public void initializeStorage(Storage storage) {
        storage.initialize();
    }

    @Override
    public StorageCreatedEvent saveStorage(Storage storage, StorageInfo storageInfo) {
        storage.saveSuccessfulStorage(storageInfo);
        return new StorageCreatedEvent(storage, ZonedDateTime.now());
    }

    @Override
    public StorageRejectedEvent rejectedSaveStorage(Storage storage, String error) {
        storage.rejectedSaveStorage(error);
        return new StorageRejectedEvent(storage,ZonedDateTime.now());
    }

}
