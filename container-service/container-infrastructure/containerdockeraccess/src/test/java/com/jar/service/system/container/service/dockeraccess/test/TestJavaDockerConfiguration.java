package com.jar.service.system.container.service.dockeraccess.test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.jar.service.system.container.service.dockeraccess.helper.DockerUsageCalculator;
import com.jar.service.system.container.service.dockeraccess.httpclient.ApacheDockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class TestJavaDockerConfiguration {

    @Value("${ec2.docker.access.url}")
    private String accessUrl;

    @Bean
    public DockerClientConfig config() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(accessUrl)
                .withDockerTlsVerify("0")
                .withDockerTlsVerify(false)
                .build();
    }

    @Bean
    public DockerUsageCalculator dockerUsageCalculator() {
        return new DockerUsageCalculator();
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


    @Bean(name = "Java-Docker")
    public DockerClient dockerClient2() {
        return DockerClientImpl.getInstance(config(),dockerHttpClient());
    }

}
