package com.jar.service.system.storage.service.jpa.entity;

import com.jar.service.system.common.domain.valueobject.StorageStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Table(name = "STORAGE_ENTITY")
@NoArgsConstructor
public class StorageEntity {
    @Id
    @Column(name = "STORAGE_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID storageId;
    @Column(name = "APP_ORDER_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID appOrderId;

    @Column(name = "USER_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "FILE_URL")
    private String fileUrl;
    @Column(name = "FILE_TYPE")
    private String fileType;

    @Enumerated(value = EnumType.STRING)
    private StorageStatus storageStatus;
    @Column(name = "ERROR",columnDefinition = "VARCHAR(1024) default ''")
    private String error;

    @Builder
    public StorageEntity(UUID storageId, UUID appOrderId, UUID userId, String filename, String fileUrl,
                         String fileType, StorageStatus storageStatus, String error) {
        this.storageId = storageId;
        this.appOrderId = appOrderId;
        this.userId = userId;
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.storageStatus = storageStatus;
        this.error = error;
    }
}
