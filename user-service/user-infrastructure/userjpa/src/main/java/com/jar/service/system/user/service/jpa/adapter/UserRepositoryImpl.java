package com.jar.service.system.user.service.jpa.adapter;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.jpa.entity.UserEntity;
import com.jar.service.system.user.service.jpa.mapper.UserDataAccessMapper;
import com.jar.service.system.user.service.jpa.repository.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserDataAccessMapper userDataAccessMapper;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository,
                              UserDataAccessMapper userDataAccessMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userDataAccessMapper = userDataAccessMapper;
    }

    @Override
    public User save(User user) {
        UserEntity save = userJpaRepository.save(userDataAccessMapper.convertUserEntityToUser(user));
        log.info("Save new User's email : {}",save.getEmail());
        return userDataAccessMapper.convertUserToUserEntity(save);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        log.info("find user by id : {}",userId.getValue());
        return userJpaRepository.findUserEntityByUserId(userId.getValue())
                .map(userDataAccessMapper::convertUserToUserEntity);
    }

    @Override
    public void delete(User user) {
        log.warn("user delete User's email is {}", user.getEmail());
        userJpaRepository.delete(userDataAccessMapper.convertUserEntityToUser(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findUserEntityByEmail(email)
                .map(userDataAccessMapper::convertUserToUserEntity);
    }
}
