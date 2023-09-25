package com.jar.service.system.user.service.jpa.mapper;

import com.jar.service.system.common.domain.valueobject.*;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {

    public UserEntity convertUserEntityToUser(User user) {
        return UserEntity
                .builder()
                .email(user.getEmail())
                .userId(user.getId().getValue())
                .username(user.getUsername())
                .password(user.getPassword())
                .rawPassword(user.getRawPassword())
                .userActive(user.getUserActive())
                .activeCode(user.getVerifyEmailCode())
                .build();
    }

    public User convertUserToUserEntity(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .rawPassword(userEntity.getRawPassword())
                .email(userEntity.getEmail())
                .userId(new UserId(userEntity.getUserId()))
                .userActive(userEntity.getUserActive())
                .verifyEmailCode(userEntity.getActiveCode())
                .build();
    }
}
