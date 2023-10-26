package com.jar.service.system.apporder.service.domain.event;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.MessageType;

import java.time.ZonedDateTime;

public class AppOrderDeletedEvent extends AppOrderEvent<AppOrder> {

    public AppOrderDeletedEvent(AppOrder domainType, ZonedDateTime publishedAt) {
        super(domainType, MessageType.DELETE, publishedAt);
    }
}
