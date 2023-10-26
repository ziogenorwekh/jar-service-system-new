package com.jar.service.system.container.service.dockeraccess.helper;

import com.jar.service.system.common.domain.valueobject.DockerStatus;
import com.jar.service.system.container.service.domian.entity.Container;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class ContainerResourceCreateHelper {

    private final DockerClient dockerClient;

    @Value("${docker.loading.time}")
    private Long dockerLoadingTime;
    @Autowired
    public ContainerResourceCreateHelper(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public DockerInfo createAndStartDockerContainer(Container container, ContainerConfig containerConfig)
            throws DockerException, InterruptedException {
        DockerStatus dockerStatus;
        ContainerCreation newContainer = dockerClient.createContainer(containerConfig, container.getApplicationName());
        dockerClient.startContainer(newContainer.id());
        log.trace("docker stats -> 2 minutes before");
        Thread.sleep(dockerLoadingTime);
        log.trace("docker stats -> 2 minutes later");
        ContainerInfo containerInfo = dockerClient.inspectContainer(newContainer.id());
        dockerStatus = DockerStatus.fromString(containerInfo.state().status());
        log.trace("docker stats -> {}", dockerStatus.name());
        return DockerInfo.builder().dockerContainerId(newContainer.id()).dockerStatus(dockerStatus).build();
    }

    public HostConfig createHostConfig(Container container) {
        Map<String, List<PortBinding>> portBindings = new HashMap<>();
        portBindings.put(String.format("%s/tcp", container.getServerPort()),
                List.of(PortBinding.of("", String.valueOf(container.getServerPort()))));
        return HostConfig.builder()
                .portBindings(portBindings)
                .build();
    }

    public String createImage(Container container, File dockerfile) throws IOException, DockerException,
            InterruptedException {
        final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
        String imageName = String.format("%s:%s", container.getApplicationName(),
                container.getAppOrderId().getValue());
        String returnImages = dockerClient.build(
                Paths.get(dockerfile.getParent()),
                imageName,
                dockerfile.getName(),
                message -> {
                    final String imageId = message.buildImageId();
                    if (imageId != null) {
                        imageIdFromMessage.set(imageId);
                    }
                }
        );
        log.info("created Image is : {}", returnImages);
        return returnImages;
    }

    public ContainerConfig createContainerConfig(String image, Container container, HostConfig hostConfig) {
        return ContainerConfig.builder()
                .image(image)
                .cmd("java", "-jar", container.getApplicationName() + ".jar")
                .tty(true)
                .openStdin(true)
                .hostConfig(hostConfig)
                .build();
    }

}
