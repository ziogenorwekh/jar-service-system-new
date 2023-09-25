package com.jar.service.system.user.service.domain.event;

import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.domain.valueobject.MessageType;
import com.jar.service.system.user.service.domain.entity.User;

import java.time.ZonedDateTime;

public class UserAppOrderDeletedEvent extends DomainEvent<User> {

    public UserAppOrderDeletedEvent(User publishDomain, ZonedDateTime publishedAt) {
        super(publishDomain, MessageType.DELETE, publishedAt);
    }
}
