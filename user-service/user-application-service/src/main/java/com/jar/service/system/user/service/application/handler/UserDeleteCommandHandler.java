package com.jar.service.system.user.service.application.handler;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.application.dto.delete.UserDeleteCommand;
import com.jar.service.system.user.service.application.exception.UserNotFoundException;
import com.jar.service.system.user.service.application.ports.output.publisher.UserAppOrderDeleteApprovalEventPublisher;
import com.jar.service.system.user.service.application.ports.output.publisher.UserDatabaseDeleteApprovalEventPublisher;
import com.jar.service.system.user.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.user.service.domain.UserDomainService;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.domain.event.UserAppOrderDeletedEvent;
import com.jar.service.system.user.service.domain.event.UserDatabaseDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class UserDeleteCommandHandler {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final UserDatabaseDeleteApprovalEventPublisher userDatabaseDeleteApprovalEventPublisher;
    private final UserAppOrderDeleteApprovalEventPublisher userAppOrderDeleteApprovalEventPublisher;

    @Autowired
    public UserDeleteCommandHandler(UserRepository userRepository,
                            UserDomainService userDomainService,
                            UserDatabaseDeleteApprovalEventPublisher userDatabaseDeleteApprovalEventPublisher,
                            UserAppOrderDeleteApprovalEventPublisher userAppOrderDeleteApprovalEventPublisher) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.userDatabaseDeleteApprovalEventPublisher = userDatabaseDeleteApprovalEventPublisher;
        this.userAppOrderDeleteApprovalEventPublisher = userAppOrderDeleteApprovalEventPublisher;
    }
    @Transactional
    public void deleteUser(UserDeleteCommand userDeleteCommand) {
        User user = userRepository.findById(new UserId(userDeleteCommand.getUserId()))
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("user not found by id : %s",
                                userDeleteCommand.getUserId())));
        UserDatabaseDeletedEvent userDatabaseDeletedEvent = userDomainService.requestDeleteUserDatabase(user);
        UserAppOrderDeletedEvent userAppOrderDeletedEvent = userDomainService.deleteUsersAppOrder(user);
        userDatabaseDeleteApprovalEventPublisher.publish(userDatabaseDeletedEvent);
        userAppOrderDeleteApprovalEventPublisher.publish(userAppOrderDeletedEvent);

        userRepository.delete(user);
    }
}
