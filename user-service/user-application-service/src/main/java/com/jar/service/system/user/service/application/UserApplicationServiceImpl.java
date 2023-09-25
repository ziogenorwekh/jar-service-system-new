package com.jar.service.system.user.service.application;

import com.jar.service.system.user.service.application.security.CustomUserDetails;
import com.jar.service.system.user.service.application.dto.create.UserCreateCommand;
import com.jar.service.system.user.service.application.dto.create.UserCreateResponse;
import com.jar.service.system.user.service.application.dto.delete.UserDeleteCommand;
import com.jar.service.system.user.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.user.service.application.dto.track.TrackUserResponse;
import com.jar.service.system.user.service.application.dto.update.UserUpdateCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeGenerationCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeVerificationCommand;
import com.jar.service.system.user.service.application.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserCreateCommandHandler userCreateCommandHandler;
    private final UserTrackCommandHandler userTrackCommandHandler;
    private final UserUpdateCommandHandler userUpdateCommandHandler;
    private final UserDeleteCommandHandler userDeleteCommandHandler;


    @Autowired
    public UserApplicationServiceImpl(UserCreateCommandHandler userCreateCommandHandler,
                                      UserTrackCommandHandler userTrackCommandHandler,
                                      UserUpdateCommandHandler userUpdateCommandHandler,
                                      UserDeleteCommandHandler userDeleteCommandHandler) {
        this.userCreateCommandHandler = userCreateCommandHandler;
        this.userTrackCommandHandler = userTrackCommandHandler;
        this.userUpdateCommandHandler = userUpdateCommandHandler;
        this.userDeleteCommandHandler = userDeleteCommandHandler;
    }

    @Override
    public UserCreateResponse createUser(UserCreateCommand userCreateCommand) {
        return userCreateCommandHandler.createUser(userCreateCommand);
    }

    @Override
    public void updateUserPassword(UserUpdateCommand userUpdateCommand) {
        userUpdateCommandHandler.updateUserPassword(userUpdateCommand);
    }

    @Override
    public TrackUserResponse trackUser(TrackUserQuery trackUserQuery) {
        return userTrackCommandHandler.trackUser(trackUserQuery);
    }


    @Override
    public void deleteUser(UserDeleteCommand userDeleteCommand) {
        userDeleteCommandHandler.deleteUser(userDeleteCommand);
    }


}
