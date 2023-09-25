package com.jar.service.system.storage.service.application.ports.output.publisher;

import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.storage.service.domain.entity.Storage;
import org.springframework.scheduling.annotation.Async;

public interface StorageEventPublisher {

    void publish(DomainEvent<Storage> domainEvent);
}
