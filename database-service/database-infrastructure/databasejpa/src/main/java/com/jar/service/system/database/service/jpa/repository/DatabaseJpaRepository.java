package com.jar.service.system.database.service.jpa.repository;

import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.database.service.jpa.entity.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseJpaRepository extends JpaRepository<DatabaseEntity, UUID> {



    @Query("select d from DatabaseEntity d where d.userId = ?1")
    Optional<DatabaseEntity> findDatabaseEntityByUserId(UUID userId);

    @Query("select d from DatabaseEntity d where d.databaseId = ?1")
    Optional<DatabaseEntity> findDatabaseEntityByDatabaseId(UUID databaseId);

    @Query("select d from DatabaseEntity d where d.databaseUsername = ?1 or d.databaseName = ?2")
    Optional<DatabaseEntity> findDatabaseEntityByDatabaseUsernameOrDatabaseName(
            String databaseUsername, String databaseName);

}
