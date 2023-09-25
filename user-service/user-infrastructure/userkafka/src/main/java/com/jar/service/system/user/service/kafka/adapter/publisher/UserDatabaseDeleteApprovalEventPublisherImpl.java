package com.jar.service.system.user.service.kafka.adapter.publisher;

import com.jar.service.system.common.avro.model.DatabaseAvroModel;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.user.service.application.ports.output.publisher.UserDatabaseDeleteApprovalEventPublisher;
import com.jar.service.system.user.service.domain.event.UserDatabaseDeletedEvent;
import com.jar.service.system.user.service.kafka.mapper.UserMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDatabaseDeleteApprovalEventPublisherImpl implements UserDatabaseDeleteApprovalEventPublisher {

    private final KafkaPublisher<String, DatabaseAvroModel> kafkaPublisher;
    private final UserMessageMapper userMessageMapper;

    @Autowired
    public UserDatabaseDeleteApprovalEventPublisherImpl(KafkaPublisher<String, DatabaseAvroModel> kafkaPublisher,
                                                        UserMessageMapper userMessageMapper) {
        this.kafkaPublisher = kafkaPublisher;
        this.userMessageMapper = userMessageMapper;
    }

    @Override
    public void publish(UserDatabaseDeletedEvent userDatabaseDeletedEvent) {
        String userId = userDatabaseDeletedEvent.getDomainType().toString();
        DatabaseAvroModel databaseAvroModel = userMessageMapper
                .convertUserDatabaseDeletedEventToDatabaseAvroModel(userDatabaseDeletedEvent);

        kafkaPublisher.send("user-service-database-delete-topic", userId, databaseAvroModel);
        log.info("Successful send avro message.");
    }
}
