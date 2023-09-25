package com.jar.service.system.user.service.jpa.repository;

import com.jar.service.system.user.service.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    @Query("select u from UserEntity u where u.userId = ?1")
    Optional<UserEntity> findUserEntityByUserId(UUID userId);

    @Query("select u from UserEntity u where u.email = ?1")
    Optional<UserEntity> findUserEntityByEmail(String email);
}
