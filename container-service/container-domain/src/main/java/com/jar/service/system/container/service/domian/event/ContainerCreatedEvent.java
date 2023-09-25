package com.jar.service.system.container.service.domian.event;

import com.jar.service.system.common.domain.valueobject.MessageType;
import com.jar.service.system.container.service.domian.entity.Container;

import java.time.ZonedDateTime;

public class ContainerCreatedEvent extends ContainerEvent {
    public ContainerCreatedEvent(Container domainType, ZonedDateTime publishedAt) {
        super(domainType, MessageType.CREATE, publishedAt);
    }
}
