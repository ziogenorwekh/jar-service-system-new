package com.jar.service.system.user.service.jpa.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.cfg.defs.UUIDDef;

import java.sql.SQLType;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(name = "USER_ENTITY")
@Entity
public class UserEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "RAW_PASSWORD")
    private String rawPassword;

    @Column(name = "USER_ACTIVE", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean userActive;

    @Column(name = "USER_ACCOUNT_ACTIVE_CODE")
    private Integer activeCode;

    @Builder
    public UserEntity(UUID userId, String username, String email,
                      String password, String rawPassword,
                      Boolean userActive, Integer activeCode) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.rawPassword = rawPassword;
        this.userActive = userActive;
        this.activeCode = activeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
