package com.jar.service.system.container.service.dockeraccess.test;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.jar.service.system.container.service.dockeraccess.adpater.EC2DockerConnector;
import com.jar.service.system.container.service.dockeraccess.helper.ContainerResourceCreateHelper;
import com.jar.service.system.container.service.dockeraccess.helper.DockerUsageCalculator;
import com.jar.service.system.container.service.dockeraccess.helper.DockerfileCreateHelper;
import com.jar.service.system.container.service.dockeraccess.helper.EC2DockerTrackHelper;
import com.jar.service.system.container.service.dockeraccess.httpclient.ApacheDockerHttpClient;
import com.jar.service.system.container.service.dockeraccess.mapper.ContainerResponseMessageParser;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.URI;
import java.time.Duration;

@Configuration
public class TestContainerConfiguration {

//
    @Value("${ec2.docker.access.spotify}")
    private String spotifyUrl;

    @Value("${ec2.docker.access.url}")
    private String dockerjavaUrl;

    @Bean
    public EC2DockerConnector ec2DockerConnector() {
        return new EC2DockerConnector(dockerfileCreateHelper(),containerResourceCreateHelper(),dockerClient()
        ,containerResponseMessageParser(),ec2DockerTrackHelper());
    }

    @Bean
    public EC2DockerTrackHelper ec2DockerTrackHelper() {
        return new EC2DockerTrackHelper(dockerClient2(),dockerUsageCalculator());
    }
    @Primary
    @Bean(name = "Spotify-Docker")
    public DockerClient dockerClient() {
        return DefaultDockerClient.builder()
                .uri(URI.create(spotifyUrl))
                .connectionPoolSize(10)
                .build();
    }

    @Bean
    public DockerHttpClient dockerHttpClient() {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(config().getDockerHost())
                .sslConfig(config().getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
    }

    @Bean
    public DockerClientConfig config() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerjavaUrl)
                .withDockerTlsVerify("0")
                .withDockerTlsVerify(false)
                .build();
    }

    @Bean(name = "Java-Docker")
    public com.github.dockerjava.api.DockerClient dockerClient2() {
        return DockerClientImpl.getInstance(config(),dockerHttpClient());
    }

    @Bean
    public DockerfileCreateHelper dockerfileCreateHelper() {
        return new DockerfileCreateHelper();
    }

    @Bean
    public ContainerResourceCreateHelper containerResourceCreateHelper() {
        return new ContainerResourceCreateHelper(dockerClient());
    }

    @Bean
    public ContainerResponseMessageParser containerResponseMessageParser() {
        return new ContainerResponseMessageParser();
    }

    @Bean
    public DockerUsageCalculator dockerUsageCalculator() {
        return new DockerUsageCalculator();
    }
}
