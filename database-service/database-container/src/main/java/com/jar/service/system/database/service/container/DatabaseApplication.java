package com.jar.service.system.database.service.container;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

//@EnableEurekaClient

@EnableKafka
@EnableDiscoveryClient
@EntityScan(basePackages = "com.jar.service.system.database.service.jpa")
@EnableJpaRepositories(basePackages = "com.jar.service.system.database.service.jpa")
@SpringBootApplication(scanBasePackages = "com.jar.service.system")
public class DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }
}
