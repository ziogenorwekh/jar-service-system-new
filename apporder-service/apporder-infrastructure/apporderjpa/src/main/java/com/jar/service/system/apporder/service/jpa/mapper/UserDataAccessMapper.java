package com.jar.service.system.apporder.service.jpa.mapper;

import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.jpa.entity.UserEntity;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {


    public UserEntity convertUserToOnlyUserEntity(User user) {
        return UserEntity.builder()
                .userId(user.getId().getValue())
                .build();
    }

    public User convertUserToOnlyUserEntity(UserEntity userEntity) {
        return User.builder()
                .userId(new UserId(userEntity.getUserId()))
                .build();
    }
}
