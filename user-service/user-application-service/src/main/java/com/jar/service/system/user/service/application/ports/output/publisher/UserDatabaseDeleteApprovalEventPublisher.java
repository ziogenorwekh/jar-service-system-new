package com.jar.service.system.user.service.application.ports.output.publisher;

import com.jar.service.system.common.domain.event.DomainEventPublisher;
import com.jar.service.system.user.service.domain.event.UserDatabaseDeletedEvent;

public interface UserDatabaseDeleteApprovalEventPublisher extends DomainEventPublisher<UserDatabaseDeletedEvent> {

}
