package com.jar.service.system.user.service.application.mapper;

import com.jar.service.system.user.service.application.dto.create.UserTokenResponse;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.domain.valueobject.UpdatePassword;
import com.jar.service.system.user.service.application.dto.create.UserCreateCommand;
import com.jar.service.system.user.service.application.dto.create.UserCreateResponse;
import com.jar.service.system.user.service.application.dto.track.TrackUserResponse;
import com.jar.service.system.user.service.application.dto.update.UserUpdateCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDataMapper {


    public User convertUserToUserCreateCommand(UserCreateCommand userCreateCommand
            , String encryptedPassword) {
        return User.builder()
                .username(userCreateCommand.getUsername())
                .email(userCreateCommand.getEmail())
                .rawPassword(userCreateCommand.getPassword())
                .password(encryptedPassword)
                .build();
    }

    public UserCreateResponse convertUserCreateResponseToUser(User user) {
        return UserCreateResponse
                .builder()
                .userActive(user.getUserActive())
                .userId(user.getId().getValue())
                .build();
    }

    public TrackUserResponse convertUserToTrackUserResponse(User user) {
        return TrackUserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public UpdatePassword convertUserUpdateCommandToChangePassword(UserUpdateCommand userUpdateCommand
    , String encodePassword) {
        return UpdatePassword.builder()
                .currentRawPassword(userUpdateCommand.getCurrentPassword())
                .newRawPassword(userUpdateCommand.getNewPassword())
                .newEncryptedPassword(encodePassword)
                .build();
    }

    public UpdatePassword convertChangePassword(String newPwd, String encodedPwd) {
        return UpdatePassword.builder()
                .newRawPassword(newPwd)
                .newEncryptedPassword(encodedPwd)
                .build();
    }

    public UserTokenResponse convertTokenToUserTokenResponse(String token, UUID userId) {
        return UserTokenResponse.builder()
                .accessToken(token)
                .userId(userId)
                .build();
    }
}
