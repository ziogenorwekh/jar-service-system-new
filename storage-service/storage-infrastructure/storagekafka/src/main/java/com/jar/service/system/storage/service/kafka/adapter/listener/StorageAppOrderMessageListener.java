package com.jar.service.system.storage.service.kafka.adapter.listener;

import com.jar.service.system.common.avro.model.AppOrderAvroModel;
import com.jar.service.system.common.avro.model.StorageAvroModel;
import com.jar.service.system.storage.service.application.ports.input.listener.StorageAppOrderCreatedListener;
import com.jar.service.system.storage.service.application.ports.input.listener.StorageAppOrderDeleteApprovalListener;
import com.jar.service.system.storage.service.kafka.mapper.StorageMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StorageAppOrderMessageListener implements com.jar.service.system.common.kafka.listener.KafkaListener<AppOrderAvroModel> {

    private final StorageAppOrderDeleteApprovalListener storageAppOrderDeleteApprovalListener;
    private final StorageAppOrderCreatedListener storageAppOrderCreatedListener;
    private final StorageMessageMapper storageMessageMapper;

    public StorageAppOrderMessageListener(StorageAppOrderDeleteApprovalListener storageAppOrderDeleteApprovalListener,
                                          StorageAppOrderCreatedListener storageAppOrderCreatedListener,
                                          StorageMessageMapper storageMessageMapper) {
        this.storageAppOrderDeleteApprovalListener = storageAppOrderDeleteApprovalListener;
        this.storageAppOrderCreatedListener = storageAppOrderCreatedListener;
        this.storageMessageMapper = storageMessageMapper;
    }


    @Override
    @KafkaListener(topics = "apporder-service-topic", groupId = "apporder-listener-group")
    public void receive(@Payload List<AppOrderAvroModel> appOrderAvroModels,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> key) {
        key.forEach(value -> {
            log.info("StorageAppOrderMessageListener Key is : {}", value);
        });
        appOrderAvroModels.forEach(appOrderAvroModel -> {
            switch (appOrderAvroModel.getMessageType()) {
                case CREATE -> {
                    log.info("CREATE -> appOrderAvroModel -> {}", appOrderAvroModel.toString());
                    storageAppOrderCreatedListener.saveAppOrder(storageMessageMapper.
                            convertAppOrderAvroModelToAppOrderCreatedResponse(appOrderAvroModel));
                }
                case DELETE, FAIL -> {
                    log.info("DELETE OR FAIL -> appOrderAvroModel -> {}", appOrderAvroModel.toString());
                    storageAppOrderDeleteApprovalListener.deleteStorage(storageMessageMapper.
                            convertAppOrderAvroModelToStorageDeleteApprovalResponse(appOrderAvroModel));
                }
                default -> {
                    log.error("StorageAppOrderMessageListener -> apporder-service-topic error");
                }
            }
        });
    }

}
