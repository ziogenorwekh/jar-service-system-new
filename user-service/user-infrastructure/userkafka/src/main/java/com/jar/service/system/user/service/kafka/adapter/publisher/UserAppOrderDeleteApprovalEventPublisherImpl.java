package com.jar.service.system.user.service.kafka.adapter.publisher;

import com.jar.service.system.common.avro.model.UserAvroModel;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.user.service.application.ports.output.publisher.UserAppOrderDeleteApprovalEventPublisher;
import com.jar.service.system.user.service.domain.event.UserAppOrderDeletedEvent;
import com.jar.service.system.user.service.kafka.mapper.UserMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAppOrderDeleteApprovalEventPublisherImpl implements UserAppOrderDeleteApprovalEventPublisher {

    private final UserMessageMapper userMessageMapper;
    private final KafkaPublisher<String, UserAvroModel> kafkaPublisher;

    @Autowired
    public UserAppOrderDeleteApprovalEventPublisherImpl(UserMessageMapper userMessageMapper,
                                                        KafkaPublisher<String, UserAvroModel> kafkaPublisher) {
        this.userMessageMapper = userMessageMapper;
        this.kafkaPublisher = kafkaPublisher;
    }


    @Override
    public void publish(UserAppOrderDeletedEvent userAppOrderDeletedEvent) {
        String userId = userAppOrderDeletedEvent.getDomainType().getId().getValue().toString();
        UserAvroModel userAvroModel = userMessageMapper
                .convertUserAppOrderDeletedEventToUserAvroModel(userAppOrderDeletedEvent);
        kafkaPublisher.send("user-service-apporder-delete-topic", userId, userAvroModel);
    }
}
