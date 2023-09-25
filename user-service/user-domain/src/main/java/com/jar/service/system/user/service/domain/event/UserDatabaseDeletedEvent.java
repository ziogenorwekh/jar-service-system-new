package com.jar.service.system.user.service.domain.event;

import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.domain.valueobject.MessageType;
import com.jar.service.system.common.domain.valueobject.UserId;

import java.time.ZonedDateTime;

public class UserDatabaseDeletedEvent extends DomainEvent<UserId> {
    public UserDatabaseDeletedEvent(UserId userId, ZonedDateTime publishedAt) {
        super(userId, MessageType.DELETE,publishedAt);
    }
}
