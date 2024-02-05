package com.jar.service.system.container.service.dockeraccess.test;


import com.jar.service.system.common.domain.valueobject.*;
import com.jar.service.system.container.service.dockeraccess.SpotifyDockerClientConfiguration;
import com.jar.service.system.container.service.dockeraccess.helper.DockerfileCreateHelper;
import com.jar.service.system.container.service.domian.entity.Container;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.NEVER,
        classes = {TestContainerConfiguration.class, SpotifyDockerClientConfiguration.class})
@ActiveProfiles(profiles = "test")
public class SpotifyDockerConnectionTest {


//    @Autowired
//    private AmazonEC2InstanceInDocker amazonEC2InstanceInDocker;

    @Autowired
    private DockerfileCreateHelper dockerfileCreateHelper;

//    @Autowired
//    private DockerClient dockerClient;
    private final UUID containerId = UUID.randomUUID();
    private final UUID appOrderId = UUID.randomUUID();

    private Container startContainer;
    private Container deleteContainer;

    @Value("${test.access.s3.url}")
    private String accessUrl;

    @BeforeEach
    public void startOf() {
        startContainer = Container.builder()
                .serverPort(10020)
                .containerStatus(ContainerStatus.INITIALIZED)
                .applicationName("testweb")
                .dockerContainerId(new DockerContainerId(""))
                .s3URL(accessUrl)
                .javaVersion(17)
                .containerId(new ContainerId(containerId))
                .appOrderId(new AppOrderId(appOrderId))
                .build();
    }

    @Test
    @DisplayName("dockerfile 생성")
    public void createDockerfile() {
        File dockerfile = dockerfileCreateHelper.createDockerfile(startContainer);
        Assertions.assertNotNull(dockerfile);
        Assertions.assertTrue(dockerfile.exists());
        dockerfileCreateHelper.deleteLocalDockerfile(dockerfile);
    }

    @Test
    @DisplayName("can docker access")
    public void dockerAccess() throws DockerException, InterruptedException {
//        log.info("docker host ? -> {}", dockerClient.getHost());
//        log.info("docker ping -> {}",dockerClient.ping().toString());
    }

    @Test
    @DisplayName("docker info 확인")
    public void dockerInfoFind() throws DockerException, InterruptedException, IOException {
        Container build = Container.builder().containerId(new ContainerId(UUID.fromString("")))
                .dockerContainerId(new DockerContainerId("")).build();

    }
    @Test
    @DisplayName("docker container 생성")
    public void createDockerApp() {

//        DockerCreateResponse dockerCreateResponse = amazonEC2InstanceInDocker
//                .createApplicationDockerContainer(startContainer);
//
//        Assertions.assertNotNull(dockerCreateResponse.getDockerContainerId().getValue());
//        Assertions.assertEquals(DockerStatus.RUNNING, dockerCreateResponse.getDockerStatus());
//
//        deleteContainer = Container.builder()
//                .containerStatus(ContainerStatus.STARTED)
//                .applicationName("testweb")
//                .dockerContainerId(dockerCreateResponse.getDockerContainerId())
//                .imageId(dockerCreateResponse.getImage())
//                .s3URL(accessUrl)
//                .javaVersion(17)
//                .containerId(new ContainerId(containerId))
//                .appOrderId(new AppOrderId(appOrderId))
//                .build();
//
//        amazonEC2InstanceInDocker.deleteDockerContainer(deleteContainer);
    }

}







