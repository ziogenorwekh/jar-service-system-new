package com.jar.service.system.apporder.service.application.ports.output.repository;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.common.domain.valueobject.ContainerId;

import java.util.Optional;

public interface ContainerRepository {

    Container save(Container container);

    Optional<Container> findByAppOrderId(AppOrder appOrder);

    void delete(Container container);

    void deleteByContainerId(ContainerId containerId);
}
