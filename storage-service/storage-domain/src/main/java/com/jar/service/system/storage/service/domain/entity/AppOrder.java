package com.jar.service.system.storage.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AppOrder extends AggregateRoot<AppOrderId> {

    @Builder
    public AppOrder(AppOrderId appOrderId) {
        super.setId(appOrderId);
    }
}
