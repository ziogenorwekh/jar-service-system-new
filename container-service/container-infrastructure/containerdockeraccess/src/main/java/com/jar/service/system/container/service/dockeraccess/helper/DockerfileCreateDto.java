package com.jar.service.system.container.service.dockeraccess.helper;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DockerfileCreateDto {

    private final String FIRST = "FROM alpine:latest";
    private final String RUN_1 = "RUN apk update && apk add curl";
    private final String RUN_2;
    private final String RUN_3;
    private final String EXPOSE;

    @Builder
    public DockerfileCreateDto(Integer jdkVersion, String s3URL, String applicationName, Integer serverPort) {
        this.RUN_2 = String.format("RUN apk add openjdk%s-jre", jdkVersion);
        this.RUN_3 = String.format("RUN curl -o %s.jar %s", applicationName, s3URL);
        this.EXPOSE = String.format("EXPOSE %s", serverPort);
    }

    public String writeDockerfileCommand() {
        List<String> writer = new ArrayList<>();
        writer.add(FIRST);
        writer.add(RUN_1);
        writer.add(RUN_2);
        writer.add(RUN_3);
        writer.add(EXPOSE);

        return String.join("\n", writer);
    }
}
