package com.jar.service.system.user.service.application.ports.output.publisher;


import com.jar.service.system.common.domain.event.DomainEventPublisher;
import com.jar.service.system.user.service.domain.event.UserAppOrderDeletedEvent;

public interface UserAppOrderDeleteApprovalEventPublisher extends DomainEventPublisher<UserAppOrderDeletedEvent> {

}
