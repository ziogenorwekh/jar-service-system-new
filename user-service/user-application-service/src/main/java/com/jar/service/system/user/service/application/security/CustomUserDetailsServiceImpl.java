package com.jar.service.system.user.service.application.security;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.application.exception.UserNotFoundException;
import com.jar.service.system.user.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.user.service.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return CustomUserDetails.builder()
                .user(userRepository.findByEmail(email).orElseThrow(
                        () -> new UserNotFoundException(String
                                .format("user not found by email : %s", email))
                )).build();
    }

    @Override
    public User findUserById(UUID userId) {
        return userRepository.findById(new UserId(userId))
                .orElseThrow(() -> {
                    log.error("email is not exist database by id : {}", userId);
                    throw new UserNotFoundException(String.format("user not found by id %s", userId));
                });
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("this {} is not register. ", email);
            throw new UserNotFoundException(String.format("this %s is not register. ", email));
        });
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
