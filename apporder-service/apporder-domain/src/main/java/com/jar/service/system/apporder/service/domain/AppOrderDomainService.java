package com.jar.service.system.apporder.service.domain;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.domain.event.*;
import com.jar.service.system.apporder.service.domain.valueobject.ContainerConfig;
import com.jar.service.system.apporder.service.domain.valueobject.ServerConfig;

public interface AppOrderDomainService {

    AppOrderCreatedEvent initializeAppOrder(AppOrder appOrder, User user);

    AppOrderContainerCreationApprovalEvent initializeAppOrderContainer(AppOrder appOrder,
                                                                       ContainerConfig containerConfig);

    void successfulCreationStorage(AppOrder appOrder, Storage storage);

    void successfulCreationContainer(AppOrder appOrder, Container container,
                                                              ServerConfig serverConfig);

    AppOrderFailedEvent failureCreationAppOrder(String error, AppOrder appOrder);

    AppOrderDeletedEvent deleteAppOrder(AppOrder appOrder);
}
