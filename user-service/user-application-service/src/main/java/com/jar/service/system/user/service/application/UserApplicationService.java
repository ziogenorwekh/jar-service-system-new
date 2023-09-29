package com.jar.service.system.user.service.application;

import com.jar.service.system.user.service.application.dto.create.UserCreateCommand;
import com.jar.service.system.user.service.application.dto.create.UserCreateResponse;
import com.jar.service.system.user.service.application.dto.delete.UserDeleteCommand;
import com.jar.service.system.user.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.user.service.application.dto.track.TrackUserResponse;
import com.jar.service.system.user.service.application.dto.update.UserResetPasswordCommand;
import com.jar.service.system.user.service.application.dto.update.UserUpdateCommand;
import jakarta.validation.Valid;

public interface UserApplicationService {


    UserCreateResponse createUser(@Valid UserCreateCommand userCreateCommand);

    void updateUserPassword(@Valid UserUpdateCommand userUpdateCommand);

    TrackUserResponse trackUser(@Valid TrackUserQuery trackUserQuery);

    void deleteUser(@Valid UserDeleteCommand userDeleteCommand);

    void resetPasswordByEmail(@Valid UserResetPasswordCommand userReSetPasswordCommand);
}
