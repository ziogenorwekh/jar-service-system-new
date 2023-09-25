package com.jar.service.system.container.service.application.ports.output.publisher;

import com.jar.service.system.common.domain.event.DomainEventPublisher;
import com.jar.service.system.container.service.domian.event.ContainerEvent;

public interface ContainerEventPublisher extends DomainEventPublisher<ContainerEvent> {

}
