package com.jar.service.system.apporder.service.jpa.adapter;

import com.jar.service.system.apporder.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.jpa.entity.StorageEntity;
import com.jar.service.system.apporder.service.jpa.mapper.StorageDataAccessMapper;
import com.jar.service.system.apporder.service.jpa.repository.StorageJpaRepository;
import com.jar.service.system.common.domain.valueobject.StorageId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StorageRepositoryImpl implements StorageRepository {

    private final StorageJpaRepository storageJpaRepository;
    private final StorageDataAccessMapper storageDataAccessMapper;

    @Autowired
    public StorageRepositoryImpl(StorageJpaRepository storageJpaRepository,
                                 StorageDataAccessMapper storageDataAccessMapper) {
        this.storageJpaRepository = storageJpaRepository;
        this.storageDataAccessMapper = storageDataAccessMapper;
    }

    @Override
    public Storage save(Storage storage) {
        StorageEntity storageEntity = storageDataAccessMapper.convertStorageEntityToStorage(storage);
        StorageEntity save = storageJpaRepository.save(storageEntity);
        return storageDataAccessMapper.convertStorageEntityToStorage(save);
    }

    @Override
    public Optional<Storage> findByAppOrderId(AppOrder appOrder) {
        return storageJpaRepository.findStorageEntityByAppOrderId(appOrder.getId().getValue())
                .map(storageDataAccessMapper::convertStorageEntityToStorage);
    }

    @Override
    public void delete(Storage storage) {
        StorageEntity storageEntity = storageDataAccessMapper.convertStorageEntityToStorage(storage);
        storageJpaRepository.delete(storageEntity);
    }

    @Override
    public void deleteByStorageId(StorageId storageId) {
        Optional<StorageEntity> storage = storageJpaRepository.findById(storageId.getValue());
        storage.ifPresent(storageJpaRepository::delete);
    }
}
