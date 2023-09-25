package com.jar.service.system.storage.service.jpa.adapter;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.storage.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.storage.service.domain.entity.Storage;
import com.jar.service.system.storage.service.jpa.entity.StorageEntity;
import com.jar.service.system.storage.service.jpa.mapper.StorageDataAccessMapper;
import com.jar.service.system.storage.service.jpa.repository.StorageJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StorageRepositoryImpl implements StorageRepository {


    private final StorageDataAccessMapper storageDataAccessMapper;
    private final StorageJpaRepository storageJpaRepository;

    @Autowired
    public StorageRepositoryImpl(StorageDataAccessMapper storageDataAccessMapper,
                                 StorageJpaRepository storageJpaRepository) {
        this.storageDataAccessMapper = storageDataAccessMapper;
        this.storageJpaRepository = storageJpaRepository;
    }

    @Override
    public Storage save(Storage storage) {
        StorageEntity storageEntity = storageDataAccessMapper.convertStorageToStorageEntity(storage);
        StorageEntity saved = storageJpaRepository.save(storageEntity);
        return storageDataAccessMapper.convertStorageEntityToStorage(saved);
    }

    @Override
    public void delete(Storage storage) {
        StorageEntity storageEntity = storageDataAccessMapper.convertStorageToStorageEntity(storage);
        storageJpaRepository.delete(storageEntity);
    }

    @Override
    public Optional<Storage> findByAppOrderId(AppOrderId appOrderId) {
        Optional<StorageEntity> storageEntity = storageJpaRepository.
                findStorageEntityByAppOrderId(appOrderId.getValue());

        return storageEntity.map(storageDataAccessMapper::convertStorageEntityToStorage);
    }

    @Override
    public Optional<Storage> findByFilename(String filename) {
        return storageJpaRepository.findStorageEntityByFilename(filename)
                .map(storageDataAccessMapper::convertStorageEntityToStorage);
    }

}
