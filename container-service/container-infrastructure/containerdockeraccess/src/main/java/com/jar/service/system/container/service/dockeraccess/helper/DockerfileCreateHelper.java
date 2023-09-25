package com.jar.service.system.container.service.dockeraccess.helper;

import com.jar.service.system.container.service.domian.entity.Container;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
public class DockerfileCreateHelper {

    @Value("${dockerfile.root.directory}")
    private String filePath;

    public File createDockerfile(Container container) {
        String outputFile = String.format("%s/Dockerfile-%s",
                filePath, container.getApplicationName());
        DockerfileCreateDto dockerfileCreateDTO = new DockerfileCreateDto(container.getJavaVersion(), container.getS3URL()
                , container.getApplicationName(), container.getServerPort());
        File dockerfile = null;
        try {
            dockerfile = new File(outputFile);
            BufferedWriter writer = new BufferedWriter(new FileWriter(dockerfile));
            writer.write(dockerfileCreateDTO.writeDockerfileCommand());
            writer.flush();
            writer.close();

        } catch (IOException e) {
            log.error("create dockerfile error message is : {}", e.getMessage());
        }
        return dockerfile;
    }

    public void deleteLocalDockerfile(File dockerfile) {
        if (dockerfile.delete()) {
            log.info("file deleted by name is : {}", dockerfile.getName());
        }
    }
}
