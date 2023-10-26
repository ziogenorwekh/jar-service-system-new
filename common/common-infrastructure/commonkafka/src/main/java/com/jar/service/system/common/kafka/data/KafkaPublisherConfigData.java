package com.jar.service.system.common.kafka.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-publisher-config")
public class KafkaPublisherConfigData {

    private String keySerializer;
    private String valueSerializer;
    private String autoRegisterSchemas;
    private String retriesConfig;
    private String acksConfig;
    private String requestTimeoutMsConfig;
    private String maxBlockMsConfig;
}
