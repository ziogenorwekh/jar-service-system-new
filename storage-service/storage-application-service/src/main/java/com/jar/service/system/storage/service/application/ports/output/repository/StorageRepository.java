package com.jar.service.system.storage.service.application.ports.output.repository;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.storage.service.domain.entity.Storage;

import java.util.Optional;

public interface StorageRepository {

    Storage save(Storage storage);

    void delete(Storage storage);

    Optional<Storage> findByAppOrderId(AppOrderId appOrderId);

    Optional<Storage> findByFilename(String filename);
}
