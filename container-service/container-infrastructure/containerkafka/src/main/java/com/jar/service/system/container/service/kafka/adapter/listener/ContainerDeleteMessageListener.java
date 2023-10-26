package com.jar.service.system.container.service.kafka.adapter.listener;

import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.container.service.application.dto.message.ContainerDeleteApprovalResponse;
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
public class ContainerDeleteMessageListener implements com.jar.service.system.common.kafka.listener.KafkaListener<AppOrderAvroModel> {

    private final ContainerDeleteMessageResponseListener containerDeleteMessageResponseListener;
    private final ContainerMessageMapper containerMessageMapper;

    @Autowired
    public ContainerDeleteMessageListener(ContainerDeleteMessageResponseListener containerDeleteMessageResponseListener,
                                          ContainerMessageMapper containerMessageMapper) {
        this.containerDeleteMessageResponseListener = containerDeleteMessageResponseListener;
        this.containerMessageMapper = containerMessageMapper;
    }


    @Override
    @KafkaListener(topics = "apporder-service-topic",groupId = "container-listener-group")
    public void receive(@Payload List<AppOrderAvroModel> appOrderAvroModels,
                                    @Header(KafkaHeaders.RECEIVED_KEY) List<String> key) {
        key.forEach(value -> {
            log.info("ContainerMessageListener Key is : {}", value);
        });
        try {
            appOrderAvroModels.forEach(appOrderAvroModel -> {
                log.info("receive AppOrderAvroModel.");
                switch (appOrderAvroModel.getMessageType()) {
                    case DELETE -> {
                        ContainerDeleteApprovalResponse containerDeleteApprovalResponse = containerMessageMapper
                                .convertAppOrderAvroModelToContainerDeleteApprovalResponse(appOrderAvroModel);
                        containerDeleteMessageResponseListener.deleteContainerApproval(containerDeleteApprovalResponse);
                    }
                    case FAIL, REJECT, CREATE, NO_DEF, UPDATE -> {
                        log.warn("Container-Service only access AppOrderAvroModel's type -> DELETE");
                    }
                    default -> log.warn("Unknown message type by receive apporder-service-topic");
                }
            });
        } catch (KafkaException | SerializationException | RecordTooLargeException e) {
            log.error("container listener error is : {}", e.getMessage());
        }
    }
}
