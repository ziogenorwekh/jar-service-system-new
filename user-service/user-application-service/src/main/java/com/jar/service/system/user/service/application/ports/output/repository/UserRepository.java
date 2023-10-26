package com.jar.service.system.user.service.application.ports.output.repository;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.domain.entity.User;

import java.util.Optional;

public interface UserRepository {


    User save(User user);

    Optional<User> findById(UserId userId);

    void delete(User user);

    Optional<User> findByEmail(String email);
}
