package com.jar.service.system.apporder.service.jpa.entity;

import com.jar.service.system.common.domain.valueobject.StorageStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
@Table(name = "STORAGE_INFO")
public class StorageEntity {

    @Id
    @Column(name = "STORAGE_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID storageId;

    @Column(name = "APP_ORDER_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID appOrderId;

    private String filename;
    private String fileUrl;
    private String fileType;

    @Enumerated(EnumType.STRING)
    private StorageStatus storageStatus;

    @Column(name = "ERROR",columnDefinition = "VARCHAR(255) default ''")
    private String error;

    @Builder
    private StorageEntity(UUID storageId, UUID appOrderId, String filename,
                          String fileUrl, String fileType,
                          StorageStatus storageStatus, String error) {
        this.storageId = storageId;
        this.appOrderId = appOrderId;
//        this.appOrderEntity = appOrderEntity;
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.storageStatus = storageStatus;
        this.error = error;
    }
}
