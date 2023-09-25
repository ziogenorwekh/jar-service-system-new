package com.jar.service.system.user.service.kafka.adapter.listener;

import com.jar.service.system.apporder.service.application.AppOrderMessageProcessor;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.common.avro.model.StorageAvroModel;
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
public class AppOrderStorageMessageListener implements com.jar.service.system.common.kafka.listener.KafkaListener<StorageAvroModel> {

    private final AppOrderMessageProcessor appOrderMessageProcessor;
    private final AppOrderMessageMapper appOrderMessageMapper;

    @Autowired
    public AppOrderStorageMessageListener(AppOrderMessageProcessor appOrderMessageProcessor,
                                          AppOrderMessageMapper appOrderMessageMapper) {
        this.appOrderMessageProcessor = appOrderMessageProcessor;
        this.appOrderMessageMapper = appOrderMessageMapper;
    }

    // 손봐야됌
    @Override
    @KafkaListener(topics = "storage-service-topic",groupId = "apporder-listener-group")
    public void receive(@Payload List<StorageAvroModel> storageAvroModels,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> key) {

        storageAvroModels.forEach(storageAvroModel -> {
            StorageApprovalResponse storageApprovalResponse = appOrderMessageMapper.
                    convertStorageAvroModelToStorageApprovalResponse(storageAvroModel);
            switch (storageAvroModel.getStorageStatus()) {
                case SAVED -> {
                    log.trace("StorageMessageListener Saved.");
                    appOrderMessageProcessor.createStorageStep(storageApprovalResponse);
                }
                case REJECTED -> {
                    log.warn("StorageMessageListener Rejected.");
                    appOrderMessageProcessor.failureStep(storageApprovalResponse);
                }
            }
        });
    }
}
