package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.exception.AppOrderNotFoundException;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.event.AppOrderFailedEvent;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
public abstract class AppOrderMessageHelper {

    private final AppOrderDomainService appOrderDomainService;
    private final AppOrderRepository appOrderRepository;


    @Autowired
    public AppOrderMessageHelper(AppOrderDomainService appOrderDomainService,
                                 AppOrderRepository appOrderRepository) {
        this.appOrderDomainService = appOrderDomainService;
        this.appOrderRepository = appOrderRepository;
    }

    public AppOrderFailedEvent failureProcessing(AppOrder appOrder, String error) {
        log.trace("appOrder Failed Event -> {}", appOrder.toString());
        AppOrderFailedEvent appOrderFailedEvent = appOrderDomainService
                .failureCreationAppOrder(error, appOrder);
        AppOrder savedAppOrder = appOrderRepository.save(appOrder);
        return appOrderFailedEvent;
    }

    @Transactional(readOnly = true)
    public AppOrder findAppOrder(UUID appOrderId) {
        AppOrder appOrder = appOrderRepository.findByAppOrderId(new AppOrderId(appOrderId))
                .orElseThrow(() -> new AppOrderNotFoundException(String
                        .format("AppOrder is not found by id : %s", appOrderId)));
        log.trace("find App Order is  : {}", appOrder.toString());
        return appOrder;
    }

}
