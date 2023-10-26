package com.jar.service.system.storage.service.kafka.adapter.publisher;

import com.jar.service.system.common.avro.model.StorageAvroModel;
import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.kafka.producer.KafkaPublisher;
import com.jar.service.system.storage.service.application.ports.output.publisher.StorageEventPublisher;
import com.jar.service.system.storage.service.domain.entity.Storage;
import com.jar.service.system.storage.service.kafka.mapper.StorageMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StorageEventPublisherImpl implements StorageEventPublisher {

    private final KafkaPublisher<String, StorageAvroModel> kafkaPublisher;
    private final StorageMessageMapper storageMessageMapper;

    @Autowired
    public StorageEventPublisherImpl(KafkaPublisher<String, StorageAvroModel> kafkaPublisher,
                                     StorageMessageMapper storageMessageMapper) {
        this.kafkaPublisher = kafkaPublisher;
        this.storageMessageMapper = storageMessageMapper;
    }

    @Override
    public void publish(DomainEvent<Storage> domainEvent) {
        Storage storage = domainEvent.getDomainType();
        String storageId = storage.getId().getValue().toString();
        StorageAvroModel storageAvroModel = storageMessageMapper
                .convertStorageToStorageAvroModel(storage);
        kafkaPublisher.send("storage-service-topic", storageId, storageAvroModel);
    }
}
