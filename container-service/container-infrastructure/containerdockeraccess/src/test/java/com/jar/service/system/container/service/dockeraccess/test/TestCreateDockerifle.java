package com.jar.service.system.container.service.dockeraccess.test;

import com.jar.service.system.container.service.dockeraccess.helper.DockerfileCreateDto;
import com.jar.service.system.container.service.domian.entity.Container;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@TestComponent
public class TestCreateDockerifle {


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

    public void createTarFile(String tarFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(tarFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             TarArchiveOutputStream tos = new TarArchiveOutputStream(bos)) {

            Files.walk(Paths.get(filePath))
                    .filter(path -> !Files.isDirectory(path)) // 디렉토리는 제외하고 파일만 처리
                    .forEach(path -> {
                        File file = path.toFile();
                        TarArchiveEntry tarEntry = new TarArchiveEntry(file, file.getName());
                        try {
                            tos.putArchiveEntry(tarEntry);
                            Files.copy(path, tos);
                            tos.closeArchiveEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            tos.finish();
        }
    }
}
