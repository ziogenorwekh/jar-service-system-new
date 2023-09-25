package com.jar.service.system.apporder.service.application.handler;

import com.jar.service.system.apporder.service.application.dto.message.UserDeleteApprovalResponse;
import com.jar.service.system.apporder.service.application.exception.AppOrderNotOwnerException;
import com.jar.service.system.apporder.service.application.mapper.AppOrderDataMapper;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderDeleteApprovalPublisher;
import com.jar.service.system.apporder.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.application.dto.delete.AppOrderDeleteCommand;
import com.jar.service.system.apporder.service.application.exception.AppOrderNotFoundException;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.domain.event.AppOrderDeletedEvent;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class AppOrderDeleteHandler {

    private final AppOrderRepository appOrderRepository;
    private final AppOrderDeleteApprovalPublisher appOrderDeleteApprovalPublisher;
    private final AppOrderDomainService appOrderDomainService;
    private final AppOrderDataMapper appOrderDataMapper;
    private final ContainerRepository containerRepository;
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppOrderDeleteHandler(AppOrderRepository appOrderRepository,
                                 AppOrderDeleteApprovalPublisher appOrderDeleteApprovalPublisher,
                                 AppOrderDomainService appOrderDomainService,
                                 AppOrderDataMapper appOrderDataMapper,
                                 ContainerRepository containerRepository,
                                 StorageRepository storageRepository, UserRepository userRepository) {
        this.appOrderRepository = appOrderRepository;
        this.appOrderDeleteApprovalPublisher = appOrderDeleteApprovalPublisher;
        this.appOrderDomainService = appOrderDomainService;
        this.appOrderDataMapper = appOrderDataMapper;
        this.containerRepository = containerRepository;
        this.storageRepository = storageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteAppOrder(AppOrderDeleteCommand appOrderDeleteCommand) {
        AppOrder appOrder = validateAppOrderAndOwner(appOrderDeleteCommand);
        AppOrderDeletedEvent appOrderDeletedEvent = appOrderDomainService.deleteAppOrder(appOrder);
        appOrderDeleteApprovalPublisher.publish(appOrderDeletedEvent);
        appOrderRepository.delete(appOrder);
    }

    @Transactional
    public void deleteAllAppOrder(UserDeleteApprovalResponse userDeleteApprovalResponse) {
        User user = appOrderDataMapper
                .convertUserDeleteApprovalResponseToUser(userDeleteApprovalResponse);
        List<AppOrder> appOrders = appOrderRepository.findAllByUserId(user);
        appOrders.forEach(appOrder -> {
            storageRepository.deleteByStorageId(appOrder.getStorageId());
            containerRepository.deleteByContainerId(appOrder.getContainerId());
            appOrderRepository.delete(appOrder);
        });
        userRepository.delete(user);
    }

    private AppOrder validateAppOrderAndOwner(AppOrderDeleteCommand appOrderDeleteCommand) {
        AppOrderId appOrderId = new AppOrderId(appOrderDeleteCommand.getAppOrderId());

        AppOrder appOrder = appOrderRepository.findByAppOrderId(appOrderId).orElseThrow(() ->
                new AppOrderNotFoundException(
                String.format("appOrder not found by appOrder id : %s", appOrderId)));
        if (!appOrder.getUserId().equals(new UserId(appOrderDeleteCommand.getUserId()))) {
            throw new AppOrderNotOwnerException("user is not granted this appOrder.");
        }
        return appOrder;
    }
}
