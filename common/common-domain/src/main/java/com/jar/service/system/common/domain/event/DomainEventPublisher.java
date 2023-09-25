package com.jar.service.system.common.domain.event;

public interface DomainEventPublisher<D extends DomainEvent> {

    void publish(D domainEvent);
}
