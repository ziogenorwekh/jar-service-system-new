package com.jar.service.system.gateway.service.container;

import com.netflix.discovery.converters.Auto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.jar.service.system")
public class APIGatewayApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(APIGatewayApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.trace("tracking token : {}",env.getProperty("server.token.secret"));
    }
}
