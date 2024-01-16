package com.jar.service.system.apporder.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
public class User extends AggregateRoot<UserId> {

    @Builder
    private User(UserId userId) {
        super.setId(userId);
    }

}
