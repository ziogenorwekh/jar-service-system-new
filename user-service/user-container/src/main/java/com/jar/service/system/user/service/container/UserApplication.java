package com.jar.service.system.user.service.container;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableDiscoveryClient
@EntityScan(basePackages = "com.jar.service.system.user.service.jpa.entity")
@EnableJpaRepositories(basePackages = "com.jar.service.system.user.service.jpa.repository")
@SpringBootApplication(scanBasePackages = "com.jar.service.system")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
