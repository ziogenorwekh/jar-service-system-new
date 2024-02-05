package com.jar.service.system.container.service.dockeraccess.test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Statistics;
import com.jar.service.system.container.service.dockeraccess.helper.DockerUsageCalculator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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


    @Test
    @DisplayName("도커 액세스")
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
        log.info("now -> {}",cpuDelta);
        Long systemCpuUsage = statistics.getCpuStats().getSystemCpuUsage();
        double v = (double) cpuDelta / systemCpuUsage * 100;
        log.info("total -> {}", systemCpuUsage);
        log.info("percent -> {}",v);
        String cpuPer = dockerUsageCalculator.calcCpuUsage(cpuDelta, systemCpuUsage);
        log.info("percent CPU -> {}",cpuPer);
        // 메모리 사용률 계산
        long memoryUsage = statistics.getMemoryStats().getUsage();
        long memoryTotal = statistics.getMemoryStats().getLimit();
        double memoryUsagePercentage = (double) memoryUsage / memoryTotal * 100;
        log.info("memory -> {}",dockerUsageCalculator.calcMemoryUsage(memoryUsage));
        log.info("Memory usage: {}% ({} out of {})", String.format("%.2f", memoryUsagePercentage));
    }

}
