package com.jar.service.system.apporder.service.application.ports.output.publisher;

import com.jar.service.system.apporder.service.domain.event.AppOrderCreatedEvent;
import com.jar.service.system.common.domain.event.DomainEventPublisher;

public interface AppOrderCreatedPublisher extends DomainEventPublisher<AppOrderCreatedEvent> {

}
