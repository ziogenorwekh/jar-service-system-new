package com.jar.service.system.container.service.kafka.adapter.publisher;

import com.jar.service.system.common.avro.model.ContainerAvroModel;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.container.service.application.ports.output.publisher.ContainerEventPublisher;
import com.jar.service.system.container.service.domian.event.ContainerEvent;
import com.jar.service.system.container.service.kafka.mapper.ContainerMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContainerEventPublisherImpl implements ContainerEventPublisher {

    private final KafkaPublisher<String, ContainerAvroModel> kafkaPublisher;
    private final ContainerMessageMapper containerMessageMapper;

    @Autowired
    public ContainerEventPublisherImpl(KafkaPublisher<String, ContainerAvroModel> kafkaPublisher,
                                       ContainerMessageMapper containerMessageMapper) {
        this.kafkaPublisher = kafkaPublisher;
        this.containerMessageMapper = containerMessageMapper;
    }


    @Override
    public void publish(ContainerEvent containerEvent) {

        log.info("ContainerEventPublisher is -> Container : {}", containerEvent.getDomainType().toString());
        ContainerAvroModel containerAvroModel = containerMessageMapper
                .convertContainerEventToContainerAvroModel(containerEvent);
        log.info("containerAvroModel is : {}", containerAvroModel.toString());
        kafkaPublisher.send("container-service-topic",
                containerAvroModel.getContainerId(), containerAvroModel);
        log.info("Successful Send ContainerEvent");
    }
}
