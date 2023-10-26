package com.jar.service.system.apporder.service.jpa.repository;

import com.jar.service.system.apporder.service.jpa.entity.ContainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ContainerJpaRepository extends JpaRepository<ContainerEntity, UUID> {

    @Query("select c from ContainerEntity c where c.appOrderId = ?1")
    Optional<ContainerEntity> findContainerEntityByAppOrderId(UUID appOrderId);
}
