package com.jar.service.system.gateway.service.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.jar.service.system")
public class APIGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(APIGatewayApplication.class, args);
    }
}
