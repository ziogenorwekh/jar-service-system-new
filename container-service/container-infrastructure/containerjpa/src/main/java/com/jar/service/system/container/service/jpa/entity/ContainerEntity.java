package com.jar.service.system.container.service.jpa.entity;

import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name = "CONTAINER_ENTITY")
public class ContainerEntity {

    @Id
    @Column(name = "CONTAINER_ID", columnDefinition = "VARCHAR(36)",unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID containerId;

    @Column(name = "APP_ORDER_ID", columnDefinition = "VARCHAR(36)", unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID appOrderId;


    @Column(name = "DOCKER_CONTAINER_ID", columnDefinition = "VARCHAR(512)")
    private String dockerContainerId;

    private String imageId;

    @Column(unique = true)
    private String applicationName;

    @Enumerated(EnumType.STRING)
    private ContainerStatus containerStatus;

    @Column(name = "ERROR", columnDefinition = "TEXT")
    private String error;
    private Integer javaVersion;
    private String s3URL;

    private Integer serverPort;

    @Builder
    public ContainerEntity(UUID containerId, UUID appOrderId, String dockerContainerId,
                           String imageId, String applicationName, ContainerStatus containerStatus,
                           String error, Integer javaVersion, String s3URL, Integer serverPort) {
        this.containerId = containerId;
        this.appOrderId = appOrderId;
        this.dockerContainerId = dockerContainerId;
        this.imageId = imageId;
        this.applicationName = applicationName;
        this.containerStatus = containerStatus;
        this.error = error;
        this.javaVersion = javaVersion;
        this.s3URL = s3URL;
        this.serverPort = serverPort;
    }
}
