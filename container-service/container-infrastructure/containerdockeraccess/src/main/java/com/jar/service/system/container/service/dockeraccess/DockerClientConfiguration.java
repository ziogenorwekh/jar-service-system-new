package com.jar.service.system.container.service.dockeraccess;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Configuration
public class DockerClientConfiguration {

    @Value("${docker.access.url}")
    private String dockerInstanceAccessUrl;

    @Bean
    public DockerClient dockerClient() {
        log.info("new DockerClient");
        log.info("instance access url : {}", dockerInstanceAccessUrl);
        return DefaultDockerClient.builder()
                .uri(URI.create(dockerInstanceAccessUrl))
                .connectionPoolSize(10)
                .build();
    }
}
