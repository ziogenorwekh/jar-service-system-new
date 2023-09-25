package com.jar.service.system.container.service.domian.event;

import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.domain.valueobject.MessageType;
import com.jar.service.system.container.service.domian.entity.Container;

import java.time.ZonedDateTime;

public class ContainerEvent extends DomainEvent<Container> {
    protected ContainerEvent(Container domainType, MessageType messageType, ZonedDateTime publishedAt) {
        super(domainType, messageType, publishedAt);
    }
}
