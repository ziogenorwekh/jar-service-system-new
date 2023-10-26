package com.jar.service.system.apporder.service.jpa.adapter;

import com.jar.service.system.apporder.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.jpa.entity.ContainerEntity;
import com.jar.service.system.apporder.service.jpa.mapper.ContainerDataAccessMapper;
import com.jar.service.system.apporder.service.jpa.repository.ContainerJpaRepository;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContainerRepositoryImpl implements ContainerRepository {

    private final ContainerJpaRepository containerJpaRepository;
    private final ContainerDataAccessMapper containerDataAccessMapper;

    @Autowired
    public ContainerRepositoryImpl(ContainerJpaRepository containerJpaRepository,
                                   ContainerDataAccessMapper containerDataAccessMapper) {
        this.containerJpaRepository = containerJpaRepository;
        this.containerDataAccessMapper = containerDataAccessMapper;
    }

    @Override
    public Container save(Container container) {
        ContainerEntity containerEntity = containerDataAccessMapper.convertContainerToContainerEntity(container);
        ContainerEntity save = containerJpaRepository.save(containerEntity);
        return containerDataAccessMapper.convertContainerToContainerEntity(save);
    }

    @Override
    public Optional<Container> findByAppOrderId(AppOrder appOrder) {
        return containerJpaRepository.findContainerEntityByAppOrderId(appOrder.getId().getValue())
                .map(containerDataAccessMapper::convertContainerToContainerEntity);
    }

    @Override
    public void delete(Container container) {
        ContainerEntity containerEntity = containerDataAccessMapper.convertContainerToContainerEntity(container);
        containerJpaRepository.delete(containerEntity);
    }

    @Override
    public void deleteByContainerId(ContainerId containerId) {
        containerJpaRepository.findById(containerId.getValue())
                .ifPresent(containerJpaRepository::delete);
    }
}
