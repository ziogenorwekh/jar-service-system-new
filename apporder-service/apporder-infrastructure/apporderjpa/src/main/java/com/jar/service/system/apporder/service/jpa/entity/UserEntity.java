package com.jar.service.system.apporder.service.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Table(name = "USER_INFO")
@Entity
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "USER_ID",columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;

    @Builder
    public UserEntity(UUID userId) {
        this.userId = userId;
    }


}
