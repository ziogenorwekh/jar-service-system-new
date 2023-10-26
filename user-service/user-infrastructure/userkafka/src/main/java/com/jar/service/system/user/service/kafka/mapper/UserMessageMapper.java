package com.jar.service.system.user.service.kafka.mapper;

import com.jar.service.system.common.avro.model.DatabaseAvroModel;
import com.jar.service.system.common.avro.model.MessageType;
import com.jar.service.system.common.avro.model.UserAvroModel;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.domain.event.UserAppOrderDeletedEvent;
import com.jar.service.system.user.service.domain.event.UserDatabaseDeletedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserMessageMapper {

    public DatabaseAvroModel convertUserDatabaseDeletedEventToDatabaseAvroModel(
            UserDatabaseDeletedEvent userDatabaseDeletedEvent) {
        return DatabaseAvroModel.newBuilder()
                .setUserId(userDatabaseDeletedEvent.getDomainType().getValue().toString())
                .setFailureMessage("")
                .build();
    }


    public UserAvroModel convertUserAppOrderDeletedEventToUserAvroModel(
            UserAppOrderDeletedEvent userAppOrderDeletedEvent) {
        User user = userAppOrderDeletedEvent.getDomainType();
        return UserAvroModel.newBuilder()
                .setUserId(user.getId().getValue().toString())
                .setMessageType(MessageType.valueOf(userAppOrderDeletedEvent.getMessageType().name()))
                .build();
    }
}
