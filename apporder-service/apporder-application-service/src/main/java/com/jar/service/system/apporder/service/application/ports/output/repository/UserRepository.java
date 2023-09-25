package com.jar.service.system.apporder.service.application.ports.output.repository;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUserId(User user);

    void delete(User user);
}
