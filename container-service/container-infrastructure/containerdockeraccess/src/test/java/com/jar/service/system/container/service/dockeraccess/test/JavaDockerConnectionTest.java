package com.jar.service.system.container.service.dockeraccess.test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateImageCmd;
import com.github.dockerjava.api.command.CreateImageResponse;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Statistics;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import com.jar.service.system.common.domain.valueobject.DockerContainerId;
import com.jar.service.system.container.service.dockeraccess.helper.DockerUsageCalculator;
import com.jar.service.system.container.service.dockeraccess.helper.DockerfileCreateHelper;
import com.jar.service.system.container.service.domian.entity.Container;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.NEVER,
        classes = {TestJavaDockerConfiguration.class})
@ActiveProfiles(profiles = "test")
public class JavaDockerConnectionTest {

    @Autowired
    @Qualifier(value = "Java-Docker")
    private DockerClient dockerClient2;

    @Autowired
    private DockerUsageCalculator dockerUsageCalculator;

    @Value("${test.access.s3.url}")
    private String accessUrl;


    @Value("${dockerfile}")
    private String filePath;

    @Autowired
    private TestCreateDockerifle testCreateDockerifle;

    @Autowired
    private DockerfileCreateHelper dockerfileCreateHelper;
    private final UUID containerId = UUID.randomUUID();
    private final UUID appOrderId = UUID.randomUUID();
    private Container startContainer;

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


//    @Test
//    @DisplayName("컨테이너 생성")
    public void containerCreate() throws IOException {
        File dockerfile = testCreateDockerifle.createDockerfile(startContainer);
        Assertions.assertNotNull(dockerfile);
        Assertions.assertTrue(dockerfile.exists());
        testCreateDockerifle.createTarFile(String.format("%s/%s.tar",dockerfile.getParent(),dockerfile.getName()));
//        log.info("parent -> {}", dockerfile.getParent());
        InputStream inputStream = new FileInputStream(new File(dockerfile.getParent(), String.format("%s.tar",dockerfile.getName())));
        CreateImageResponse helloworld = dockerClient2.createImageCmd("isimage", inputStream).exec();
        log.info("images -> {}", helloworld);
    }

    //    @Test
//    @DisplayName("도커 액세스")
    public void dockerAccess() throws ExecutionException, InterruptedException {
        CompletableFuture<Statistics> futureStats = new CompletableFuture<>();
        StatsCmd cmd = dockerClient2.statsCmd("a42b0359ea3f");

        cmd.exec(new ResultCallback<Statistics>() {
            @Override
            public void onStart(Closeable closeable) {
            }

            @Override
            public void onNext(Statistics statistics) {
                futureStats.complete(statistics); // Get the first statistics and complete the future.
                try {
                    close();
                } catch (IOException e) {
                    log.error("Error closing stats stream", e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                futureStats.completeExceptionally(throwable);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void close() throws IOException {
            }
        });

        Statistics statistics = futureStats.get(); // This will block until the future is completed
        long cpuDelta = statistics.getCpuStats().getCpuUsage().getTotalUsage().longValue();
//                - statistics.getPreCpuStats().getCpuUsage().getTotalUsage();
        log.info("now -> {}", cpuDelta);
        Long systemCpuUsage = statistics.getCpuStats().getSystemCpuUsage();
        double v = (double) cpuDelta / systemCpuUsage * 100;
        log.info("total -> {}", systemCpuUsage);
        log.info("percent -> {}", v);
        String cpuPer = dockerUsageCalculator.calcCpuUsage(cpuDelta, systemCpuUsage);
        log.info("percent CPU -> {}", cpuPer);
        // 메모리 사용률 계산
        long memoryUsage = statistics.getMemoryStats().getUsage();
        long memoryTotal = statistics.getMemoryStats().getLimit();
        double memoryUsagePercentage = (double) memoryUsage / memoryTotal * 100;
        log.info("memory -> {}", dockerUsageCalculator.calcMemoryUsage(memoryUsage));
        log.info("Memory usage: {}% ({} out of {})", String.format("%.2f", memoryUsagePercentage));
    }

}
