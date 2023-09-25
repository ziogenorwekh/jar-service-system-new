package com.jar.service.system.apporder.service.application.handler;

import com.jar.service.system.apporder.service.application.exception.AppOrderApplicationException;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderCreatedPublisher;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.application.mapper.AppOrderDataMapper;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateResponse;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.domain.event.AppOrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class AppOrderCreateHandler {

    private final AppOrderDataMapper appOrderDataMapper;
    private final AppOrderRepository appOrderRepository;
    private final AppOrderDomainService appOrderDomainService;
    private final AppOrderCreatedPublisher appOrderCreatedPublisher;
    private final UserRepository userRepository;

    @Autowired
    public AppOrderCreateHandler(AppOrderDataMapper appOrderDataMapper,
                                 AppOrderRepository appOrderRepository,
                                 AppOrderDomainService appOrderDomainService,
                                 AppOrderCreatedPublisher appOrderCreatedPublisher,
                                 UserRepository userRepository) {
        this.appOrderDataMapper = appOrderDataMapper;
        this.appOrderRepository = appOrderRepository;
        this.appOrderDomainService = appOrderDomainService;
        this.appOrderCreatedPublisher = appOrderCreatedPublisher;
        this.userRepository = userRepository;
    }

    @Transactional
    public AppOrderCreateResponse saveAppOrder(AppOrderCreateCommand appOrderCreateCommand) {

        validateDuplicatedAppOrderApplicationName(appOrderCreateCommand);
        validateDuplicatedAppOrderServerPort(appOrderCreateCommand);

        AppOrder appOrder = appOrderDataMapper
                .convertAppOrderCreateCommandToAppOrder(appOrderCreateCommand);
        User user = appOrderDataMapper.convertAppOrderCreateCommandToUser(appOrderCreateCommand);

        AppOrderCreatedEvent appOrderCreatedEvent = appOrderDomainService.initializeAppOrder(appOrder, user);
        appOrderCreatedPublisher.publish(appOrderCreatedEvent);
        AppOrder savedAppOrder = appOrderRepository.save(appOrder);
        User save = userRepository.save(user);
        log.trace("user saved is : {}", user.getId().getValue());
        return appOrderDataMapper.convertAppOrderToAppOrderCreateResponse(savedAppOrder);
    }

    private void validateDuplicatedAppOrderApplicationName(AppOrderCreateCommand appOrderCreateCommand) {
        Optional<AppOrder> optionalAppOrder = appOrderRepository
                .findByApplicationName(appOrderCreateCommand);
        if (optionalAppOrder.isPresent()) {
            throw new AppOrderApplicationException("Already Exists ApplicationName");
        }
    }

    private void validateDuplicatedAppOrderServerPort(AppOrderCreateCommand appOrderCreateCommand) {
        Optional<AppOrder> appOrderOptional = appOrderRepository.findByServerPort(appOrderCreateCommand);
        if (appOrderOptional.isPresent()) {
            throw new AppOrderApplicationException("Already Use serverPort");
        }
    }
}
