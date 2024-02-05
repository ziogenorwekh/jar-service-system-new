package com.jar.service.system.container.service.dockeraccess;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.jar.service.system.container.service.dockeraccess.httpclient.ApacheDockerHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class JavaDockerClientConfiguration {


    @Value("${docker.java.url}")
    private String javaDockerAccessUrl;

    @Bean
    public DockerClientConfig config() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(javaDockerAccessUrl)
                .withDockerTlsVerify("0")
                .withDockerTlsVerify(false)
                .build();
    }

    @Bean
    public DockerHttpClient dockerHttpClient() {
        log.info("Java-Docker");
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(config().getDockerHost())
                .sslConfig(config().getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
    }


    @Bean(name = "Java-Docker")
    public com.github.dockerjava.api.DockerClient dockerClient() {
        return DockerClientImpl.getInstance(config(),dockerHttpClient());
    }
}
