package com.jar.service.system.apporder.service.jpa.repository;

import com.jar.service.system.apporder.service.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
}
