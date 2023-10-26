package com.jar.service.system.apporder.service.jpa.entity;


import com.jar.service.system.common.domain.valueobject.ApplicationStatus;
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
@Table(name = "APP_ORDER_ENTITY")
public class AppOrderEntity {

    @Id
    @Column(name = "APP_ORDER_ID", columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID appOrderId;

    @Column(name = "STORAGE_ID", columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID storageId;

    @Column(name = "CONTAINER_ID", columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID containerId;

    @Column(name = "USER_ID", columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    private String endPoint;
    private String applicationName;
    private Integer serverPort;

    private Integer javaVersion;

//    @Column(name = "ERROR", columnDefinition = "VARCHAR(1024) default ''")
    @Lob
    @Column(name = "ERROR",columnDefinition = "TEXT")
    private String error;

    @Builder
    private AppOrderEntity(UUID appOrderId, ApplicationStatus applicationStatus, String endPoint,
                           UUID storageId, UUID userId, UUID containerId,
                           String applicationName, Integer serverPort, Integer javaVersion, String error) {
        this.appOrderId = appOrderId;
        this.storageId = storageId;
        this.userId = userId;
        this.containerId = containerId;
        this.applicationStatus = applicationStatus;
        this.endPoint = endPoint;
        this.applicationName = applicationName;
        this.serverPort = serverPort;
        this.javaVersion = javaVersion;
        this.error = error;
    }

    @Override
    public String toString() {
        return "AppOrderEntity{" +
                "appOrderId=" + appOrderId +
                ", storageId=" + storageId +
                ", containerId=" + containerId +
                ", userId=" + userId +
                ", applicationStatus=" + applicationStatus +
                ", endPoint='" + endPoint + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", serverPort=" + serverPort +
                ", javaVersion=" + javaVersion +
                ", error='" + error + '\'' +
                '}';
    }
}
