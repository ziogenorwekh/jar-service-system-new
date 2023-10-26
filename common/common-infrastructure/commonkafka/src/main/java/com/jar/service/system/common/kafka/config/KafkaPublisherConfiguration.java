package com.jar.service.system.common.kafka.config;


import com.jar.service.system.common.kafka.data.KafkaConfigData;
import com.jar.service.system.common.kafka.data.KafkaPublisherConfigData;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration

public class KafkaPublisherConfiguration<K extends Serializable,V extends SpecificRecordBase> {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaPublisherConfigData kafkaPublisherConfigData;

    @Autowired
    public KafkaPublisherConfiguration(KafkaConfigData kafkaConfigData,
                                       KafkaPublisherConfigData kafkaPublisherConfigData) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaPublisherConfigData = kafkaPublisherConfigData;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
        properties.put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaPublisherConfigData.getKeySerializer());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaPublisherConfigData.getValueSerializer());
        properties.put(ProducerConfig.RETRIES_CONFIG, kafkaPublisherConfigData.getRetriesConfig());
        properties.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS,kafkaPublisherConfigData.getAutoRegisterSchemas());
        properties.put(ProducerConfig.ACKS_CONFIG, kafkaPublisherConfigData.getAcksConfig()); // Wait for all in-sync replicas to ack
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaPublisherConfigData.getRequestTimeoutMsConfig()); // Increase request timeout
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, kafkaPublisherConfigData.getMaxBlockMsConfig()); // Max time to block on sending (milliseconds)properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092,localhost:29092");
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
