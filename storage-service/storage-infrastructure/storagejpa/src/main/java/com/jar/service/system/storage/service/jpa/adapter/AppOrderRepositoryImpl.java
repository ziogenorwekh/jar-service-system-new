package com.jar.service.system.storage.service.jpa.adapter;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.storage.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.storage.service.domain.entity.AppOrder;
import com.jar.service.system.storage.service.jpa.entity.AppOrderEntity;
import com.jar.service.system.storage.service.jpa.mapper.StorageDataAccessMapper;
import com.jar.service.system.storage.service.jpa.repository.AppOrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AppOrderRepositoryImpl implements AppOrderRepository {

    private final AppOrderJpaRepository appOrderJpaRepository;
    private final StorageDataAccessMapper storageDataAccessMapper;

    @Autowired
    public AppOrderRepositoryImpl(AppOrderJpaRepository appOrderJpaRepository,
                                  StorageDataAccessMapper storageDataAccessMapper) {
        this.appOrderJpaRepository = appOrderJpaRepository;
        this.storageDataAccessMapper = storageDataAccessMapper;
    }

    @Override
    public AppOrder save(AppOrder appOrder) {
        AppOrderEntity saved = appOrderJpaRepository.save(storageDataAccessMapper
                .convertAppOrderToAppOrderEntity(appOrder));
        return storageDataAccessMapper.convertAppOrderEntityToAppOrder(saved);
    }

    @Override
    public Optional<AppOrder> findByAppOrderId(AppOrderId appOrderId) {
        return appOrderJpaRepository.findById(appOrderId.getValue())
                .map(storageDataAccessMapper::convertAppOrderEntityToAppOrder);
    }

    @Override
    public void delete(AppOrder appOrder) {
        AppOrderEntity appOrderEntity = storageDataAccessMapper.convertAppOrderToAppOrderEntity(appOrder);
        appOrderJpaRepository.delete(appOrderEntity);
    }
}
