package com.jar.service.system.common.domain.valueobject;

public enum DockerStatus {

    RUNNING, EXITED, PAUSED, CREATED, RESTARTING, REMOVING, DEAD, STARTING, UNKNOWN_ERROR;

    public static DockerStatus fromString(String status) {
        return switch (status.toLowerCase()) {
            case "running" -> RUNNING;
            case "exited" -> EXITED;
            case "paused" -> PAUSED;
            case "created" -> CREATED;
            case "restarting" -> RESTARTING;
            case "removing" -> REMOVING;
            case "dead" -> DEAD;
            case "starting" -> STARTING;
            default -> throw new IllegalArgumentException("Invalid DockerStatus: " + status);
        };
    }

}
