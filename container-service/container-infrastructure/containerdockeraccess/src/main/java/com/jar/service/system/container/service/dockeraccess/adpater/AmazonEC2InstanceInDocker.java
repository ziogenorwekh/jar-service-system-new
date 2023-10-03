package com.jar.service.system.container.service.dockeraccess.adpater;

import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.common.domain.valueobject.DockerStatus;
import com.jar.service.system.container.service.application.dto.connect.DockerCreateResponse;
import com.jar.service.system.container.service.application.dto.connect.DockerUsage;
import com.jar.service.system.container.service.application.exception.ContainerApplicationException;
import com.jar.service.system.container.service.application.ports.output.dockeraccess.InstanceDockerAccess;
import com.jar.service.system.container.service.dockeraccess.exception.DockerContainerDuplicatedException;
import com.jar.service.system.container.service.application.exception.ContainerDockerStateException;
import com.jar.service.system.container.service.dockeraccess.helper.ContainerResourceCreateHelper;
import com.jar.service.system.container.service.dockeraccess.helper.DockerInfo;
import com.jar.service.system.container.service.dockeraccess.helper.DockerUsageCalculator;
import com.jar.service.system.container.service.dockeraccess.helper.DockerfileCreateHelper;
import com.jar.service.system.container.service.dockeraccess.mapper.ContainerResponseMessageParser;
import com.jar.service.system.container.service.domian.entity.Container;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.exceptions.DockerRequestException;
import com.spotify.docker.client.messages.*;
import com.spotify.docker.client.shaded.javax.ws.rs.ProcessingException;
import com.spotify.docker.client.shaded.org.apache.http.conn.HttpHostConnectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class AmazonEC2InstanceInDocker implements InstanceDockerAccess {

    private final DockerfileCreateHelper dockerfileCreateHelper;
    private final ContainerResourceCreateHelper containerResourceCreateHelper;
    private final DockerClient dockerClient;
    private final ContainerResponseMessageParser containerResponseMessageParser;
    private final DockerUsageCalculator dockerUsageCalculator;

    public AmazonEC2InstanceInDocker(DockerfileCreateHelper dockerfileCreateHelper,
                                     ContainerResourceCreateHelper containerResourceCreateHelper,
                                     DockerClient dockerClient,
                                     ContainerResponseMessageParser containerResponseMessageParser,
                                     DockerUsageCalculator dockerUsageCalculator) {
        this.dockerfileCreateHelper = dockerfileCreateHelper;
        this.containerResourceCreateHelper = containerResourceCreateHelper;
        this.dockerClient = dockerClient;
        this.containerResponseMessageParser = containerResponseMessageParser;
        this.dockerUsageCalculator = dockerUsageCalculator;
    }

    @Override
    public DockerCreateResponse createApplicationDockerContainer(Container container) {
        DockerStatus dockerStatus;
        String dockerContainerId = "";
        String returnedImageId = null;
        String error = "";
        try {
            validateDuplicatedApplicationName(container);
            File dockerfile = dockerfileCreateHelper.createDockerfile(container);
            returnedImageId = containerResourceCreateHelper.createImage(container, dockerfile);
            HostConfig hostConfig = containerResourceCreateHelper.createHostConfig(container);
            ContainerConfig containerConfig = containerResourceCreateHelper
                    .createContainerConfig(returnedImageId, container, hostConfig);
            DockerInfo dockerInfo = containerResourceCreateHelper.
                    createAndStartDockerContainer(container, containerConfig);
            dockerStatus = dockerInfo.getDockerStatus();
            switch (dockerStatus) {
                case EXITED, DEAD, PAUSED -> {
                    String logs = this
                            .trackLogsContainer(new DockerContainerId(dockerInfo.getDockerContainerId()));
                    try {
                        error = extractErrorClass(logs);
                    } catch (Exception e) {
                        error = "Unknown Error.";
                    }
                }
            }
            log.trace("error is : {}", error);
            dockerContainerId = dockerInfo.getDockerContainerId();
            dockerfileCreateHelper.deleteLocalDockerfile(dockerfile);
        } catch (DockerRequestException e) {
            log.error("CREATE DOCKER -> DockerRequestException e -> type : {}, message : {}",
                    e.getClass(), e.getMessage());
            return new DockerCreateResponse(new DockerContainerId(dockerContainerId),
                    DockerStatus.UNKNOWN_ERROR, containerResponseMessageParser.parser(e.getResponseBody()), "");
        } catch (HttpHostConnectException | ProcessingException | DockerException e) {
            log.error("CREATE DOCKER -> HttpHostConnectException, ProcessingException e -> type : {}, message : {}",
                    e.getClass(), e.getMessage());
            return new DockerCreateResponse(new DockerContainerId(dockerContainerId),
                    DockerStatus.UNKNOWN_ERROR, "Docker server client access error.", "");
        } catch (IOException | InterruptedException e) {
            log.warn("Exception type is : {}", e.getClass());
            log.error("CREATE DOCKER -> DockerException,IOException,InterruptedException e -> type : {}, message : {}",
                    e.getClass(), e.getMessage());
            return new DockerCreateResponse(new DockerContainerId(dockerContainerId),
                    DockerStatus.UNKNOWN_ERROR, "Internal Server Exception", "");
        }
        return new DockerCreateResponse(new DockerContainerId(dockerContainerId),
                dockerStatus, error, returnedImageId);
    }

    @Override
    public void deleteDockerContainer(Container container) {
        try {
            log.trace("delete Container data is : {}", container.toString());
            log.trace("Docker Container dockerId is : {}", container.getDockerContainerId().getValue());
            if (container.getContainerStatus() == ContainerStatus.STARTED) {
                dockerClient.stopContainer(container.getDockerContainerId().getValue(), 15);
            }
            dockerClient.removeContainer(container.getDockerContainerId().getValue());
            dockerClient.removeImage(container.getImageId());
        } catch (DockerException | InterruptedException e) {
            log.error("Container Delete error message is : {}", e.getMessage());
            throw new ContainerDockerStateException(e.getMessage());
        }
    }

    @Override
    public DockerUsage trackContainer(Container container) {
        try {
            ContainerStats stats = dockerClient.stats(container.getDockerContainerId().getValue());
            Long currentCpuUsage = stats.cpuStats().cpuUsage().percpuUsage().get(0);
            Long totalCpuSize = stats.cpuStats().systemCpuUsage();
            log.trace("this container data -> current : {}, total : {}", currentCpuUsage, totalCpuSize);
            Long rss = stats.memoryStats().stats().rss();

            return DockerUsage.builder()
                    .cpuUsage(dockerUsageCalculator.calcCpuUsage(currentCpuUsage, totalCpuSize))
                    .memoryUsage(dockerUsageCalculator.calcMemoryUsage(rss))
                    .build();
        } catch (DockerException | InterruptedException e) {
            log.error("Container Delete error message is : {}", e.getMessage());
            throw new ContainerDockerStateException(e.getMessage());
        }
    }

    @Override
    public String trackLogsContainer(DockerContainerId dockerContainerId) {
        String logs;
        try (LogStream stream = dockerClient.
                logs(dockerContainerId.getValue(),
                        DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr())) {
            logs = stream.readFully();
        } catch (DockerException | InterruptedException e) {
            throw new ContainerApplicationException(e.getMessage());
        }
        return logs;
    }

    @Override
    public DockerStatus stopContainer(Container container) {
        log.trace("start container by id : {} application : {}", container.getDockerContainerId(),
                container.getApplicationName());
        try {
            dockerClient.stopContainer(container.getDockerContainerId().getValue(), 15);
            ContainerInfo containerInfo = dockerClient.inspectContainer(container.getDockerContainerId().getValue());
            return DockerStatus.fromString(containerInfo.state().status());
        } catch (DockerException | InterruptedException e) {
            log.error("Container Delete error message is : {}", e.getMessage());
            throw new ContainerDockerStateException(e.getMessage());
        }


    }

    @Override
    public DockerStatus startContainer(Container container) {
        log.trace("start container by id : {} application : {}", container.getDockerContainerId(),
                container.getApplicationName());
        try {
            dockerClient.startContainer(container.getDockerContainerId().getValue());
            ContainerInfo containerInfo = dockerClient.inspectContainer(container.getDockerContainerId().getValue());
            return DockerStatus.fromString(containerInfo.state().status());
        } catch (DockerException | InterruptedException e) {
            throw new ContainerDockerStateException(e.getMessage());
        }
    }

    private void validateDuplicatedApplicationName(Container container) throws DockerException, InterruptedException {
        List<com.spotify.docker.client.messages.Container> containers = dockerClient.listContainers();
        containers.forEach(dockerContainer -> {
            String existDockerContainerName = dockerContainer.names().get(0).substring(1);
            log.info("existDockerContainerName : {}", existDockerContainerName);
            if (container.getApplicationName().equals(existDockerContainerName)) {
                log.warn("TRUE duplicated container name.");
                throw new DockerContainerDuplicatedException("ApplicationName duplicated.");
            }
        });
    }

    private String extractErrorClass(String errorLog) {
        List<String> classNames = new ArrayList<>();

        // 정규 표현식 패턴
        Pattern pattern = Pattern.compile("([\\w.]+Exception)");
        Matcher matcher = pattern.matcher(errorLog);

        while (matcher.find()) {
            // 매칭된 부분을 리스트에 추가
            classNames.add(matcher.group(1));
        }

        if (classNames.isEmpty()) {
            classNames.add("No exception class names found in error log");
        }
        return String.join(", ", classNames);
    }
}
