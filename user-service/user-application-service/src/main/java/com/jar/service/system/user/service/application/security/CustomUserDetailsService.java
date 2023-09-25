package com.jar.service.system.user.service.application.security;

import com.jar.service.system.user.service.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {

    User findUserById(UUID userId);

    User findByEmail(String email);

    @Transactional
    void save(User user);
}
