package com.jar.service.system.container.service.application.ports.output.repository;


import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.container.service.domian.entity.Container;

import java.util.Optional;

public interface ContainerRepository {

    Container save(Container container);

    Optional<Container> findByApplicationNameOrDockerContainerId(Container container);

    Optional<Container> findByContainerId(ContainerId containerId);

    Optional<Container> findByAppOrderId(AppOrderId appOrderId);

    void delete(Container container);
}
