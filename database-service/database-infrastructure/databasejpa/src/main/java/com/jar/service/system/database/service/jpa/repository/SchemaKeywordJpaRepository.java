package com.jar.service.system.database.service.jpa.repository;

import com.jar.service.system.database.service.jpa.entity.SchemaKeywordEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchemaKeywordJpaRepository extends CrudRepository<SchemaKeywordEntity, Long> {

}
