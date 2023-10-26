package com.jar.service.system.user.service.kafka.adapter.publisher;

import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderContainerCreatedEventPublisher;
import com.jar.service.system.apporder.service.domain.event.AppOrderCreatedContainerEvent;
import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.common.avro.model.ContainerAvroModel;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.user.service.kafka.mapper.AppOrderMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AppOrderContainerCreatedEventPublisherImpl implements AppOrderContainerCreatedEventPublisher {

    private final KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher;
    private final AppOrderMessageMapper appOrderMessageMapper;

    public AppOrderContainerCreatedEventPublisherImpl(KafkaPublisher<String, AppOrderAvroModel> kafkaPublisher,
                                                      AppOrderMessageMapper appOrderMessageMapper) {
        this.kafkaPublisher = kafkaPublisher;
        this.appOrderMessageMapper = appOrderMessageMapper;
    }

    @Override
    public void publish(AppOrderCreatedContainerEvent domainEvent) {
        log.info("Ex send.");
    }
}
