package com.jar.service.system.container.service.jpa.repository;

import com.jar.service.system.container.service.jpa.entity.ContainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ContainerJpaRepository extends JpaRepository<ContainerEntity, UUID> {


    @Query("select c from ContainerEntity c where c.applicationName = ?1 or c.dockerContainerId = ?2")
    Optional<ContainerEntity> findContainerEntityByApplicationNameOrAndDockerContainerId(
            String applicationName, String dockerContainerId);

    @Query("select c from ContainerEntity c where c.appOrderId = ?1")
    Optional<ContainerEntity> findContainerEntityByAppOrderId(UUID appOrderId);
}
