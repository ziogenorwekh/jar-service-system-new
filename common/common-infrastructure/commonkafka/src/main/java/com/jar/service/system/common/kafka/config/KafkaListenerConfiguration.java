package com.jar.service.system.common.kafka.config;

import com.jar.service.system.common.kafka.data.KafkaConfigData;
import com.jar.service.system.common.kafka.data.KafkaListenerConfigData;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaListenerConfiguration<K extends Serializable, V extends SpecificRecordBase> {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaListenerConfigData kafkaListenerConfigData;

    @Autowired
    public KafkaListenerConfiguration(KafkaConfigData kafkaConfigData,
                                      KafkaListenerConfigData kafkaListenerConfigData) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaListenerConfigData = kafkaListenerConfigData;
    }

    @Bean
    public ConsumerFactory<K, V> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaConfigData.getSchemaRegistryUrl());
        props.put(kafkaListenerConfigData.getSpecificAvroReaderKey(), kafkaListenerConfigData.getSpecificAvroReader());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaListenerConfigData.getAutoOffsetReset());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaListenerConfigData.getGroupIdConfig());
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, kafkaListenerConfigData.getAllowAutoCreateTopicsConfig());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaListenerConfigData.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaListenerConfigData.getValueDeserializer());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, kafkaListenerConfigData.getAutoAvroReaderConfig());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<K, V> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<K, V> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        concurrentKafkaListenerContainerFactory.setBatchListener(kafkaListenerConfigData.getBatchListener());
        concurrentKafkaListenerContainerFactory.setConcurrency(kafkaListenerConfigData.getConcurrencyLevel());
        concurrentKafkaListenerContainerFactory.setAutoStartup(kafkaListenerConfigData.getAutoStartup());
        concurrentKafkaListenerContainerFactory.getContainerProperties()
                .setPollTimeout(kafkaListenerConfigData.getPollTimeoutMs());
        return concurrentKafkaListenerContainerFactory;
    }

}
