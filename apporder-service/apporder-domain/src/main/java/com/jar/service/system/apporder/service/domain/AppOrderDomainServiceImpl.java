package com.jar.service.system.apporder.service.domain;


import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.domain.event.*;
import com.jar.service.system.apporder.service.domain.exception.AppOrderDomainException;
import com.jar.service.system.apporder.service.domain.valueobject.ContainerConfig;
import com.jar.service.system.apporder.service.domain.valueobject.ServerConfig;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class AppOrderDomainServiceImpl implements AppOrderDomainService {

    @Override
    public AppOrderCreatedEvent initializeAppOrder(AppOrder appOrder, User user) {
        appOrder.initializeAppOrder(user);
        log.info("initialize appOrder, id : {}, status : {}",
                appOrder.getId().getValue(),appOrder.getApplicationStatus());
        return new AppOrderCreatedEvent(appOrder, ZonedDateTime.now());
    }

    @Override
    public AppOrderContainerCreationApprovalEvent initializeAppOrderContainer(
            AppOrder appOrder,
            ContainerConfig containerConfig) throws AppOrderDomainException {
        Container container = appOrder.initializeContainer(containerConfig);
        log.info("initialize container, id : {} ", container.getId());
        return new AppOrderContainerCreationApprovalEvent(container, ZonedDateTime.now());
    }

    @Override
    public void successfulCreationStorage(AppOrder appOrder, Storage storage) throws AppOrderDomainException {
        log.info("created storage by id : {}", storage.getId());
        appOrder.saveStorage(storage);
    }

    @Override
    public AppOrderCreatedContainerEvent successfulCreationContainer(
            AppOrder appOrder, Container container, ServerConfig serverConfig) throws AppOrderDomainException {
        appOrder.startedContainer(container,serverConfig);
        log.info("successful container by id : {}, and appOrder state is : {}"
                , container.getId(), appOrder.getApplicationStatus());
        return new AppOrderCreatedContainerEvent(appOrder, ZonedDateTime.now());
    }

    /**
     * This Event takes AppOrder Tracking Service
     *
     * @param error
     * @param appOrder
     * @return
     */
    @Override
    public AppOrderFailedEvent failureCreationAppOrder(String error, AppOrder appOrder) {
        log.error("appOrder creating failed by error message is : {}", error);
        appOrder.addErrorMessage(error);
        return new AppOrderFailedEvent(appOrder, ZonedDateTime.now());
    }

    @Override
    public AppOrderDeletedEvent deleteAppOrder(AppOrder appOrder) {
        return new AppOrderDeletedEvent(appOrder, ZonedDateTime.now());
    }
}