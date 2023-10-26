package com.jar.service.system.container.service.kafka.adapter.listener;

import com.jar.service.system.common.avro.model.ContainerAvroModel;
import com.jar.service.system.container.service.application.dto.message.ContainerCreationApprovalResponse;
import com.jar.service.system.container.service.application.ports.input.listener.ContainerCreationMessageResponseListener;
import com.jar.service.system.container.service.application.ports.input.listener.ContainerDeleteMessageResponseListener;
import com.jar.service.system.container.service.kafka.mapper.ContainerMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RecordTooLargeException;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ContainerCreateMessageListener implements com.jar.service.system.common.kafka.listener.KafkaListener<ContainerAvroModel> {

    private final ContainerCreationMessageResponseListener containerCreationMessageResponseListener;
    private final ContainerMessageMapper containerMessageMapper;


    @Autowired
    public ContainerCreateMessageListener(ContainerCreationMessageResponseListener containerCreationMessageResponseListener,
                                          ContainerMessageMapper containerMessageMapper) {
        this.containerCreationMessageResponseListener = containerCreationMessageResponseListener;
        this.containerMessageMapper = containerMessageMapper;
    }

    @Override
    @KafkaListener(topics = "apporder-service-container-topic",groupId = "container-listener-group")
    public void receive(@Payload List<ContainerAvroModel> containerAvroModels,
                                     @Header(KafkaHeaders.RECEIVED_KEY) List<String> key) {
        try {
            containerAvroModels.forEach(containerAvroModel -> {
                log.info("ContainerAvroModel is : {}", containerAvroModel.toString());
                ContainerCreationApprovalResponse containerCreationApprovalResponse = containerMessageMapper
                        .convertContainerAvroModelToContainerApprovalResponse(containerAvroModel);

                containerCreationMessageResponseListener.createContainerApproval(containerCreationApprovalResponse);
            });
        } catch (SerializationException | RecordTooLargeException | KafkaException e) {
            log.error("container listener error is : {}", e.getMessage());
        }
    }

}
