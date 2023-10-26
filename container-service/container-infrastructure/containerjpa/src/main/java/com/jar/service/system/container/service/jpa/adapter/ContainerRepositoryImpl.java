package com.jar.service.system.container.service.jpa.adapter;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.container.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.container.service.domian.entity.Container;
import com.jar.service.system.container.service.jpa.entity.ContainerEntity;
import com.jar.service.system.container.service.jpa.mapper.ContainerDataAccessMapper;
import com.jar.service.system.container.service.jpa.repository.ContainerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
        ContainerEntity saved = containerJpaRepository
                .save(containerDataAccessMapper.convertContainerToContainerEntity(container));
        return containerDataAccessMapper.convertContainerEntityToContainer(saved);
    }

    @Override
    public Optional<Container> findByApplicationNameOrDockerContainerId(Container container) {
        return containerJpaRepository.findContainerEntityByApplicationNameOrAndDockerContainerId(container.getApplicationName(),
                container.getDockerContainerId().getValue())
                .map(containerDataAccessMapper::convertContainerEntityToContainer);
    }

    @Override
    public Optional<Container> findByContainerId(ContainerId containerId) {
        return containerJpaRepository.findById(containerId.getValue())
                .map(containerDataAccessMapper::convertContainerEntityToContainer);
    }

    @Override
    public Optional<Container> findByAppOrderId(AppOrderId appOrderId) {
        return containerJpaRepository.findContainerEntityByAppOrderId(appOrderId.getValue())
                .map(containerDataAccessMapper::convertContainerEntityToContainer);
    }

    @Override
    public void delete(Container container) {
        ContainerEntity containerEntity = containerDataAccessMapper
                .convertContainerToContainerEntity(container);
        containerJpaRepository.delete(containerEntity);
    }
}
