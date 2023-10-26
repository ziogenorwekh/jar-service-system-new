package com.jar.service.system.common.kafka.data;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-listener-config")
public class KafkaListenerConfigData {

    private String specificAvroReaderKey;
    private String specificAvroReader;
    private String autoOffsetReset;
    private String groupIdConfig;
    private String allowAutoCreateTopicsConfig;
    private String keyDeserializer;
    private String valueDeserializer;
    private String autoAvroReaderConfig;

    private Boolean batchListener;
    private Boolean autoStartup;
    private Integer concurrencyLevel;
    private Long pollTimeoutMs;
}
