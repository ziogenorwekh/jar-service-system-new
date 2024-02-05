package com.jar.service.system.container.service.dockeraccess;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.jar.service.system.container.service.dockeraccess.httpclient.ApacheDockerHttpClient;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.URI;
import java.time.Duration;

@Slf4j
@Configuration
public class SpotifyDockerClientConfiguration {

    @Value("${docker.access.url}")
    private String dockerInstanceAccessUrl;

    @Primary
    @Bean(name = "Spotify-Docker")
    public DockerClient dockerClient() {
        log.info("Spotify-Docker");
        return DefaultDockerClient.builder()
                .uri(URI.create(dockerInstanceAccessUrl))
                .connectionPoolSize(10)
                .build();
    }

}
