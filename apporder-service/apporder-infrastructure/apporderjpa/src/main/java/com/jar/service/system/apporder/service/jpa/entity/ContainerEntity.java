package com.jar.service.system.apporder.service.jpa.entity;

import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor
@Table(name = "CONTAINER_INFO")
public class ContainerEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID containerId;

    @Column(name = "APP_ORDER_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID appOrderId;

    private String dockerContainerId;

    private String s3URL;

    private Integer javaVersion;
    @Enumerated(EnumType.STRING)
    private ContainerStatus containerStatus;

    private String applicationName;

    private Integer serverPort;

    @Column(name = "ERROR",columnDefinition = "VARCHAR(255) default ''")
    private String error;

    @Builder
    public ContainerEntity(UUID containerId, UUID appOrderId, String dockerContainerId,
                           String s3URL, Integer javaVersion, ContainerStatus containerStatus,
                           String applicationName, Integer serverPort, String error) {
        this.containerId = containerId;
        this.appOrderId = appOrderId;
//        this.appOrderEntity = appOrderEntity;
        this.dockerContainerId = dockerContainerId;
        this.s3URL = s3URL;
        this.javaVersion = javaVersion;
        this.containerStatus = containerStatus;
        this.applicationName = applicationName;
        this.serverPort = serverPort;
        this.error = error;
    }
}
