package com.jar.service.system.apporder.service.application.ports.output.repository;

import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.common.domain.valueobject.AppOrderId;

import java.util.List;
import java.util.Optional;

public interface AppOrderRepository {

    AppOrder save(AppOrder appOrder);

    Optional<AppOrder> findByAppOrderId(AppOrderId appOrderId);

    List<AppOrder> findAllByUserId(User user);

    Optional<AppOrder> findByApplicationName(AppOrderCreateCommand appOrderCreateCommand);

    Optional<AppOrder> findByServerPort(AppOrderCreateCommand appOrderCreateCommand);

    void delete(AppOrder appOrder);

}
