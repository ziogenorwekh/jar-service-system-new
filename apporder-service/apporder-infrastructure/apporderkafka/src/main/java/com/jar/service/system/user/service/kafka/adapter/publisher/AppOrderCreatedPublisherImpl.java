package com.jar.service.system.user.service.kafka.adapter.publisher;

import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderCreatedPublisher;
import com.jar.service.system.apporder.service.domain.event.AppOrderCreatedEvent;
import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.user.service.kafka.mapper.AppOrderMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppOrderCreatedPublisherImpl implements AppOrderCreatedPublisher {

    private final KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher;
    private final AppOrderMessageMapper appOrderMessageMapper;

    @Autowired
    public AppOrderCreatedPublisherImpl(KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher,
                                        AppOrderMessageMapper appOrderMessageMapper) {
        this.kafkaPublisher = kafkaPublisher;
        this.appOrderMessageMapper = appOrderMessageMapper;
    }

    @Override
    public void publish(AppOrderCreatedEvent appOrderCreatedEvent) {
        String appOrderId = appOrderCreatedEvent.getDomainType().getId().toString();
        AppOrderAvroModel appOrderAvroModel = appOrderMessageMapper
                .convertAppOrderCreatedEventToAppOrderAvroModel(appOrderCreatedEvent);
        kafkaPublisher.send("apporder-service-topic", appOrderId, appOrderAvroModel);
        log.info("AppOrderCreate Avro Model is : {}", appOrderAvroModel.toString());
        log.info("AppOrderCreatedPublisher sent.");
    }
}
