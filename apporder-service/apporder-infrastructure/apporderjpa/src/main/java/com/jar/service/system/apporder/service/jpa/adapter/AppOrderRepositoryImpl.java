package com.jar.service.system.apporder.service.jpa.adapter;

import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.jpa.entity.AppOrderEntity;
import com.jar.service.system.apporder.service.jpa.mapper.AppOrderDataAccessMapper;
import com.jar.service.system.apporder.service.jpa.repository.AppOrderJpaRepository;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class AppOrderRepositoryImpl implements AppOrderRepository {

    private final AppOrderJpaRepository appOrderJpaRepository;
    private final AppOrderDataAccessMapper appOrderDataAccessMapper;

    @Autowired
    public AppOrderRepositoryImpl(AppOrderJpaRepository appOrderJpaRepository,
                                  AppOrderDataAccessMapper appOrderDataAccessMapper) {
        this.appOrderJpaRepository = appOrderJpaRepository;
        this.appOrderDataAccessMapper = appOrderDataAccessMapper;
    }

    @Override
    public AppOrder save(AppOrder appOrder) {
        AppOrderEntity appOrderEntity = appOrderDataAccessMapper.convertAppOrderToAppOrderEntity(appOrder);
        AppOrderEntity saved = appOrderJpaRepository.save(appOrderEntity);
        log.info("AppOrderEntity : {}", saved);
        return appOrderDataAccessMapper.convertAppOrderEntityToAppOrder(saved);
    }

    @Override
    public Optional<AppOrder> findByAppOrderId(AppOrderId appOrderId) {
        return appOrderJpaRepository.findAppOrderEntityByAppOrderId(appOrderId.getValue())
                .map(appOrderDataAccessMapper::convertAppOrderEntityToAppOrder);
    }

    @Override
    public List<AppOrder> findAllByUserId(User user) {
        return appOrderJpaRepository.findAllByUserId(user.getId().getValue())
                .stream().map(appOrderDataAccessMapper::convertAppOrderEntityToAppOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppOrder> findByApplicationName(AppOrderCreateCommand appOrderCreateCommand) {
        return appOrderJpaRepository.findAppOrderEntityByApplicationName(appOrderCreateCommand.getApplicationName()).
                map(appOrderDataAccessMapper::convertAppOrderEntityToAppOrder);
    }

    @Override
    public Optional<AppOrder> findByServerPort(AppOrderCreateCommand appOrderCreateCommand) {
        return appOrderJpaRepository.findAppOrderEntityByServerPort(appOrderCreateCommand.getServerPort()).
                map(appOrderDataAccessMapper::convertAppOrderEntityToAppOrder);
    }

    @Override
    public void delete(AppOrder appOrder) {
        appOrderJpaRepository.delete(appOrderDataAccessMapper.convertAppOrderToAppOrderEntity(appOrder));
    }

}
