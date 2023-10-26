package com.jar.service.system.storage.service.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Table(name = "APP_ORDER_INFO")
@NoArgsConstructor
public class AppOrderEntity {

    @Id
    @Column(name = "APP_ORDER_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID appOrderId;

    @Builder
    private AppOrderEntity(UUID appOrderId) {
        this.appOrderId = appOrderId;
    }
}
