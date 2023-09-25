package com.jar.service.system.user.service.application;

import com.jar.service.system.user.service.application.dto.create.UserCreateCommand;
import com.jar.service.system.user.service.application.dto.create.UserCreateResponse;
import com.jar.service.system.user.service.application.dto.delete.UserDeleteCommand;
import com.jar.service.system.user.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.user.service.application.dto.track.TrackUserResponse;
import com.jar.service.system.user.service.application.dto.update.UserUpdateCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeGenerationCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeVerificationCommand;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;

public interface UserApplicationService {


    UserCreateResponse createUser(@Valid UserCreateCommand userCreateCommand);

    void updateUserPassword(@Valid UserUpdateCommand userUpdateCommand);

    TrackUserResponse trackUser(@Valid TrackUserQuery trackUserQuery);

    void deleteUser(@Valid UserDeleteCommand userDeleteCommand);
}
