package com.jar.service.system.container.service.dockeraccess.test;

import com.jar.service.system.container.service.dockeraccess.helper.DockerUsageCalculator;
import com.jar.service.system.container.service.dockeraccess.helper.DockerfileCreateHelper;
import com.jar.service.system.container.service.dockeraccess.mapper.ContainerResponseMessageParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContainerConfiguration {


    @Value("${ec2.docker.access.url}")
    private String accessUrl;
//    @Bean
//    public AmazonEC2InstanceInDocker amazonEC2InstanceInDocker() {
//        return new AmazonEC2InstanceInDocker(dockerfileCreateHelper(),containerResourceCreateHelper(),dockerClient()
//        ,containerResponseMessageParser(),dockerUsageCalculator());
//    }
//
//    @Bean
//    public DockerClient dockerClient() {
//        return DefaultDockerClient.builder()
//                .uri(URI.create(accessUrl))
//                .connectionPoolSize(10)
//                .build();
//    }

    @Bean
    public DockerfileCreateHelper dockerfileCreateHelper() {
        return new DockerfileCreateHelper();
    }

//    @Bean
//    public ContainerResourceCreateHelper containerResourceCreateHelper() {
//        return new ContainerResourceCreateHelper(dockerClient());
//    }

    @Bean
    public ContainerResponseMessageParser containerResponseMessageParser() {
        return new ContainerResponseMessageParser();
    }

    @Bean
    public DockerUsageCalculator dockerUsageCalculator() {
        return new DockerUsageCalculator();
    }
}
