package com.jar.service.system.apporder.service.domain.event;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.common.domain.valueobject.MessageType;

import java.time.ZonedDateTime;

public class AppOrderCreatedEvent extends AppOrderEvent<AppOrder> {

    public AppOrderCreatedEvent(AppOrder appOrder, ZonedDateTime createAt) {

        super(appOrder, MessageType.CREATE, createAt);
    }
}
