package com.jar.service.system.apporder.service.jpa.repository;

import com.jar.service.system.apporder.service.jpa.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface StorageJpaRepository extends JpaRepository<StorageEntity, UUID> {

    @Query("select s from StorageEntity s where s.appOrderId = ?1")
    Optional<StorageEntity> findStorageEntityByAppOrderId(UUID appOrderId);
}
