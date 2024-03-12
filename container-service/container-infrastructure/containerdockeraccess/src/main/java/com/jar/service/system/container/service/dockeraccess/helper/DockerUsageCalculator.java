package com.jar.service.system.container.service.dockeraccess.helper;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DockerUsageCalculator {

    private final String per = "%";

    public String calcCpuUsage(Long current, Long total) {
        double usage = (current / (double) total) * 100.0;
        return String.format("%.2f%s", usage, per);
    }

    public String calcMemoryUsage(Long rss) {
        long usage = rss / 1024 / 1024;
        double percentage = (usage / (double) 512) * 100.0;
        return String.format("%.2f%s", (double) percentage, per);
    }

}
