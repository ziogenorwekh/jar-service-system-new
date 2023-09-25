package com.jar.service.system.container.service.container;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;



@EnableKafka
@EnableDiscoveryClient
//@ComponentScan(basePackages = "com.jar.service.system")
@EntityScan(basePackages = "com.jar.service.system.container.service.jpa.entity")
@EnableJpaRepositories(basePackages = "com.jar.service.system.container.service.jpa.repository")
@SpringBootApplication(scanBasePackages = "com.jar.service.system")
public class ContainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerApplication.class, args);
    }
}
