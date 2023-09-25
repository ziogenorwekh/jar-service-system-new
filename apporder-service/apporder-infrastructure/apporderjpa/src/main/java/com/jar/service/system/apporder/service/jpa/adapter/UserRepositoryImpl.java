package com.jar.service.system.apporder.service.jpa.adapter;

import com.jar.service.system.apporder.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.jpa.entity.UserEntity;
import com.jar.service.system.apporder.service.jpa.mapper.UserDataAccessMapper;
import com.jar.service.system.apporder.service.jpa.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {


    private final UserDataAccessMapper userDataAccessMapper;
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepositoryImpl(UserDataAccessMapper userDataAccessMapper, UserJpaRepository userJpaRepository) {
        this.userDataAccessMapper = userDataAccessMapper;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userDataAccessMapper.convertUserToOnlyUserEntity(user);
        UserEntity save = userJpaRepository.save(userEntity);
        return userDataAccessMapper.convertUserToOnlyUserEntity(save);
    }

    @Override
    public Optional<User> findByUserId(User user) {
        return userJpaRepository.findById(user.getId().getValue())
                .map(userDataAccessMapper::convertUserToOnlyUserEntity);
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userDataAccessMapper.convertUserToOnlyUserEntity(user);
        userJpaRepository.delete(userEntity);
    }
}
