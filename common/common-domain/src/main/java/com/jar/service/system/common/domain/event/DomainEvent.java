package com.jar.service.system.common.domain.event;

import com.jar.service.system.common.domain.valueobject.MessageType;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public abstract class DomainEvent<D> {

    private final D domainType;

    private final MessageType messageType;
    private final ZonedDateTime publishedAt;

    protected DomainEvent(D domainType, MessageType messageType, ZonedDateTime publishedAt) {
        this.domainType = domainType;
        this.messageType = messageType;
        this.publishedAt = publishedAt;
    }
}
