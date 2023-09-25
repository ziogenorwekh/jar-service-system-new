package com.jar.service.system.apporder.service.jpa.repository;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.jpa.entity.AppOrderEntity;
import com.jar.service.system.apporder.service.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppOrderJpaRepository extends JpaRepository<AppOrderEntity, UUID> {

    @Query("select a from AppOrderEntity a where a.appOrderId = ?1")
    Optional<AppOrderEntity> findAppOrderEntityByAppOrderId(UUID appOrderId);

    Optional<AppOrderEntity> findAppOrderEntityByApplicationName(String applicationName);

    @Query("select a from AppOrderEntity a where a.serverPort = ?1")
    Optional<AppOrderEntity> findAppOrderEntityByServerPort(Integer serverPort);

    @Query("select a from AppOrderEntity a where a.userId = ?1")
    Optional<AppOrderEntity> findAppOrderEntityByUserId(UUID userId);

    @Query("select a from AppOrderEntity a where a.userId = ?1")
    List<AppOrderEntity> findAllByUserId(UUID userId);
//    @Modifying
//    @Query("delete from AppOrderEntity a where a.userEntity = ?1")
//    void deleteAllByUserEntity(UserEntity userEntity);
}
