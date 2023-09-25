package com.jar.service.system.storage.service.domain;

import com.jar.service.system.storage.service.domain.entity.Storage;
import com.jar.service.system.storage.service.domain.event.StorageCreatedEvent;
import com.jar.service.system.storage.service.domain.event.StorageRejectedEvent;
import com.jar.service.system.storage.service.domain.valueobject.StorageInfo;

public interface StorageDomainService {


    void initializeStorage(Storage storage);

    StorageCreatedEvent saveStorage(Storage storage, StorageInfo storageInfo);

    StorageRejectedEvent rejectedSaveStorage(Storage storage,String error);
}
