package com.jar.service.system.common.kafka.listener;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

public interface KafkaListener<T extends SpecificRecordBase> {

    void receive(List<T> messaging, List<String> key);
}
