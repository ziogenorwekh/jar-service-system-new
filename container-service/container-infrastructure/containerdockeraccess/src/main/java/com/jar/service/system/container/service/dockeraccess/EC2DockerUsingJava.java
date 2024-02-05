package com.jar.service.system.container.service.dockeraccess;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Statistics;
import com.jar.service.system.container.service.application.dto.connect.DockerUsage;
import com.jar.service.system.container.service.dockeraccess.helper.DockerUsageCalculator;
import com.jar.service.system.container.service.domian.entity.Container;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class EC2DockerUsingJava {

    @Qualifier(value = "Java-Docker")
    private final DockerClient dockerClient;

    private final DockerUsageCalculator dockerUsageCalculator;

    @Autowired
    public EC2DockerUsingJava(DockerClient dockerClient, DockerUsageCalculator dockerUsageCalculator) {
        this.dockerClient = dockerClient;
        this.dockerUsageCalculator = dockerUsageCalculator;
    }


    public DockerUsage trackingDockerContainer(Container container) throws InterruptedException,ExecutionException,
            IOException {
        StatsCmd command = dockerClient.statsCmd(container.getDockerContainerId().getValue());
        CompletableFuture<Statistics> aSynchronized = synchronizeDockerConnection(command);
        Statistics statistics= aSynchronized.get();

        long currentCpuUsage = statistics.getCpuStats().getCpuUsage().getTotalUsage().longValue();
        Long systemCpuUsage = statistics.getCpuStats().getSystemCpuUsage();
        String cpuPercentage = dockerUsageCalculator.calcCpuUsage(currentCpuUsage, systemCpuUsage);

        long memoryUsage = statistics.getMemoryStats().getUsage();
        String memory = dockerUsageCalculator.calcMemoryUsage(memoryUsage);
        return DockerUsage.builder().cpuUsage(cpuPercentage).memoryUsage(memory).build();
    }

    private CompletableFuture<Statistics> synchronizeDockerConnection(StatsCmd command) {
        CompletableFuture<Statistics> futureStats = new CompletableFuture<>();
        command.exec(new ResultCallback<Statistics>() {
            @Override
            public void onStart(Closeable closeable) {
            }
            @SneakyThrows(IOException.class)
            @Override
            public void onNext(Statistics statistics) {
                futureStats.complete(statistics); // Get the first statistics and complete the future.
                close();
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
        return futureStats;
    }
}
