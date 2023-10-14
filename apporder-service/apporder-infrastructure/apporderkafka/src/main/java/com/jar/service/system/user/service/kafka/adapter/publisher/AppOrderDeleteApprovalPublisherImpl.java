package com.jar.service.system.user.service.kafka.adapter.publisher;

import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderDeleteApprovalPublisher;
import com.jar.service.system.apporder.service.domain.event.AppOrderDeletedEvent;
import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.user.service.kafka.mapper.AppOrderMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppOrderDeleteApprovalPublisherImpl implements AppOrderDeleteApprovalPublisher {

    private final AppOrderMessageMapper appOrderMessageMapper;
    private final KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher;

    @Autowired
    public AppOrderDeleteApprovalPublisherImpl(AppOrderMessageMapper appOrderMessageMapper,
                                               KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher) {
        this.appOrderMessageMapper = appOrderMessageMapper;
        this.kafkaPublisher = kafkaPublisher;
    }


    @Override
    public void publish(AppOrderDeletedEvent domainEvent) {
        String appOrderId = domainEvent.getDomainType().getId().getValue().toString();
        AppOrderAvroModel appOrderAvroModel = appOrderMessageMapper
                .convertAppOrderDeletedEventToAppOrderAvroModel(domainEvent);

        kafkaPublisher.send("apporder-service-topic", appOrderId, appOrderAvroModel);
        log.info("AppOrderDeleteApprovalPublisher sent.");
    }
}
