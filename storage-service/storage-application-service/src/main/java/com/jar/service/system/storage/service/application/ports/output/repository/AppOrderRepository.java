package com.jar.service.system.storage.service.application.ports.output.repository;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.storage.service.domain.entity.AppOrder;

import java.util.Optional;

public interface AppOrderRepository {

    AppOrder save(AppOrder appOrder);

    Optional<AppOrder> findByAppOrderId(AppOrderId appOrderId);

    void delete(AppOrder appOrder);
}
