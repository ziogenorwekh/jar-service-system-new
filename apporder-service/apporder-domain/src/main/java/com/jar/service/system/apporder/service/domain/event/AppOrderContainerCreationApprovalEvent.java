package com.jar.service.system.apporder.service.domain.event;

import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.common.domain.valueobject.MessageType;

import java.time.ZonedDateTime;

public class AppOrderContainerCreationApprovalEvent extends AppOrderEvent<Container> {

    public AppOrderContainerCreationApprovalEvent(Container domainType, ZonedDateTime publishedAt) {
        super(domainType, MessageType.CREATE ,publishedAt);
    }


}
