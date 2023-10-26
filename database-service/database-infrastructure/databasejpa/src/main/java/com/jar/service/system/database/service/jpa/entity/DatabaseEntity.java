package com.jar.service.system.database.service.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "DATABASE_ENTITY")
@NoArgsConstructor
public class DatabaseEntity {

    @Id
    @Column(name = "DATABASE_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID databaseId;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "DATABASE_NAME")
    private String databaseName;
    @Column(name = "DATABASE_USERNAME")
    private String databaseUsername;

    @Column(name = "DATABASE_PASSWORD")
    private String databasePassword;

    @Column(name = "DATABASE_ACCESS_URL")
    private String databaseAccessUrl;

    @Builder
    public DatabaseEntity(UUID databaseId,
                          UUID userId,
                          String databaseName,
                          String databaseUsername,
                          String databasePassword,
                          String databaseAccessUrl) {
        this.databaseId = databaseId;
        this.userId = userId;
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.databaseAccessUrl = databaseAccessUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseEntity that = (DatabaseEntity) o;
        return databaseId.equals(that.databaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseId);
    }
}
