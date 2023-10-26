package com.jar.service.system.apporder.service.application.ports.output.repository;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.common.domain.valueobject.StorageId;

import java.util.Optional;

public interface StorageRepository {

    Storage save(Storage storage);

    Optional<Storage> findByAppOrderId(AppOrder appOrder);

    void delete(Storage storage);

    void deleteByStorageId(StorageId storageId);
}
