package com.jar.service.system.user.service.kafka.adapter.publisher;

import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderContainerCreateApprovalPublisher;
import com.jar.service.system.apporder.service.domain.event.AppOrderContainerCreationApprovalEvent;
import com.jar.service.system.common.avro.model.ContainerAvroModel;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.user.service.kafka.mapper.AppOrderMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppOrderContainerCreateApprovalPublisherImpl implements AppOrderContainerCreateApprovalPublisher {


    private final KafkaPublisher<String, ContainerAvroModel> kafkaPublisher;
    private final AppOrderMessageMapper appOrderMessageMapper;


    @Autowired
    public AppOrderContainerCreateApprovalPublisherImpl(KafkaPublisher<String, ContainerAvroModel> kafkaPublisher,
                                                        AppOrderMessageMapper appOrderMessageMapper) {
        this.kafkaPublisher = kafkaPublisher;
        this.appOrderMessageMapper = appOrderMessageMapper;
    }

    @Override
    public void publish(AppOrderContainerCreationApprovalEvent appOrderContainerCreationApprovalEvent) {

        String appOrderId = appOrderContainerCreationApprovalEvent.getDomainType().getAppOrderId().toString();

        ContainerAvroModel containerAvroModel = appOrderMessageMapper.
                convertAppOrderContainerCreationApprovalEventToContainerAvroModel(appOrderContainerCreationApprovalEvent);

        kafkaPublisher.send("apporder-service-container-topic", appOrderId, containerAvroModel);
        log.info("AppOrderContainerCreateApprovalPublisher sent.");
    }
}
