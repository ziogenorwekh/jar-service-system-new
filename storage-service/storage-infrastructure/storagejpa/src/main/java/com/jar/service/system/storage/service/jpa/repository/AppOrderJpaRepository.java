package com.jar.service.system.storage.service.jpa.repository;

import com.jar.service.system.storage.service.jpa.entity.AppOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppOrderJpaRepository extends JpaRepository<AppOrderEntity, UUID> {

}
