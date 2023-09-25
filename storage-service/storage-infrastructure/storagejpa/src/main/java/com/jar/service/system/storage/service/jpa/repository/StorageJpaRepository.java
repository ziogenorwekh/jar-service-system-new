package com.jar.service.system.storage.service.jpa.repository;

import com.jar.service.system.storage.service.jpa.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface StorageJpaRepository extends JpaRepository<StorageEntity, UUID> {


    @Query("delete from StorageEntity s where s.appOrderId = ?1")
    void deleteStorageEntityByAppOrderId(UUID appOrderId);

    @Query("select s from StorageEntity s where s.appOrderId = ?1")
    Optional<StorageEntity> findStorageEntityByAppOrderId(UUID appOrderId);

    @Query("select s from StorageEntity s where s.filename = ?1")
    Optional<StorageEntity> findStorageEntityByFilename(String filename);
}
