package com.jar.service.system.user.service.kafka.adapter.publisher;

import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderFailedPublisher;
import com.jar.service.system.apporder.service.domain.event.AppOrderFailedEvent;
import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.user.service.kafka.mapper.AppOrderMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppOrderFailedPublisherImpl implements AppOrderFailedPublisher {

    private final KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher;
    private final AppOrderMessageMapper appOrderMessageMapper;

    @Autowired
    public AppOrderFailedPublisherImpl(KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher,
                                       AppOrderMessageMapper appOrderMessageMapper) {
        this.kafkaPublisher = kafkaPublisher;
        this.appOrderMessageMapper = appOrderMessageMapper;
    }

    @Override
    public void publish(AppOrderFailedEvent appOrderFailedEvent) {
        String appOrderId = appOrderFailedEvent.getDomainType().getId().toString();
        AppOrderAvroModel appOrderAvroModel = appOrderMessageMapper
                .convertAppOrderFailedEventToAppOrderAvroModel(appOrderFailedEvent);

        kafkaPublisher.send("apporder-service-topic", appOrderId, appOrderAvroModel);
        log.info("AppOrderFailedPublisher sent.");
    }
}
