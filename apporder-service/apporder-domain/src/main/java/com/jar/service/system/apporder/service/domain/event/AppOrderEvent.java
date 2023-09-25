package com.jar.service.system.apporder.service.domain.event;

import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.domain.valueobject.MessageType;

import java.time.ZonedDateTime;

public abstract class AppOrderEvent<T> extends DomainEvent<T> {

    public AppOrderEvent(T domainType, MessageType messageType, ZonedDateTime publishedAt) {
        super(domainType, messageType, publishedAt);
    }
}
