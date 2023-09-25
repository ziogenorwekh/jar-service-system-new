package com.jar.service.system.storage.service.domain.event;

import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.domain.valueobject.MessageType;
import com.jar.service.system.storage.service.domain.entity.Storage;

import java.time.ZonedDateTime;

public class StorageCreatedEvent extends DomainEvent<Storage> {

    public StorageCreatedEvent(Storage domainType, ZonedDateTime publishedAt) {
        super(domainType, MessageType.CREATE, publishedAt);
    }
}
