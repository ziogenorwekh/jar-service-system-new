package com.jar.service.system.user.service.kafka.adapter.listener;

import com.jar.service.system.apporder.service.application.AppOrderMessageProcessStep;
import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.common.avro.model.ContainerAvroModel;
import com.jar.service.system.user.service.kafka.mapper.AppOrderMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AppOrderContainerMessageListener implements com.jar.service.system.common.kafka.listener.KafkaListener<ContainerAvroModel> {


    private final AppOrderMessageMapper appOrderMessageMapper;
    private final AppOrderMessageProcessStep appOrderMessageProcessStep;

    @Autowired
    public AppOrderContainerMessageListener(AppOrderMessageMapper appOrderMessageMapper,
                                            AppOrderMessageProcessStep appOrderMessageProcessStep) {
        this.appOrderMessageMapper = appOrderMessageMapper;
        this.appOrderMessageProcessStep = appOrderMessageProcessStep;
    }

    @KafkaListener(topics = "container-service-topic",groupId = "apporder-listener-group")
    public void receive(@Payload List<ContainerAvroModel> containerAvroModels,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> key) {

        try {
            containerAvroModels.forEach(containerAvroModel -> {
                ContainerApprovalResponse containerApprovalResponse = appOrderMessageMapper
                        .convertContainerApprovalResponseToContainerAvroModel(containerAvroModel);
                switch (containerAvroModel.getContainerStatus()) {
                    case STARTED -> {
                        log.trace("Start ContainerAvroModel is : {}",containerAvroModel.toString());
                        appOrderMessageProcessStep.containerStep(containerApprovalResponse);
                    }
                    case STOPPED, REJECTED -> {
                        log.warn("ContainerMessageListener Rejected.");
                        log.trace("Reject Avro Data is : {}",containerAvroModel.toString());
                        appOrderMessageProcessStep.containerApprovalFailureStep(containerApprovalResponse);
                    }
                }
            });
        } catch (Exception e) {
            log.error("AppOrder Container Message Listener error message is : {}", e.getMessage());
        }
    }
}
