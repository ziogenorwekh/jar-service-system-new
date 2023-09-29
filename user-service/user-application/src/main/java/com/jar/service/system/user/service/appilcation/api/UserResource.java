package com.jar.service.system.user.service.appilcation.api;


import com.jar.service.system.user.service.application.UserApplicationService;
import com.jar.service.system.user.service.application.dto.create.UserCreateCommand;
import com.jar.service.system.user.service.application.dto.create.UserCreateResponse;
import com.jar.service.system.user.service.application.dto.delete.UserDeleteCommand;
import com.jar.service.system.user.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.user.service.application.dto.track.TrackUserResponse;
import com.jar.service.system.user.service.application.dto.update.UserResetPasswordCommand;
import com.jar.service.system.user.service.application.dto.update.UserUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class UserResource {


    private final UserApplicationService userApplicationService;

    @Autowired
    public UserResource(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserCreateResponse> join(@RequestBody UserCreateCommand userCreateCommand) {
        UserCreateResponse userCreateResponse =
                userApplicationService.createUser(userCreateCommand);
        return ResponseEntity.ok(userCreateResponse);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<TrackUserResponse> retrieveUser(@PathVariable @RequestHeader("userId") UUID userId) {
        TrackUserResponse trackUserResponse = userApplicationService
                .trackUser(TrackUserQuery.builder().userId(userId).build());
        return ResponseEntity.ok(trackUserResponse);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@PathVariable @RequestHeader("userId") UUID userId,
                                           @RequestBody UserUpdateCommand userUpdateCommand) {
        userUpdateCommand.setUserId(userId);
        userApplicationService.updateUserPassword(userUpdateCommand);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/users", method = RequestMethod.PATCH)
    public ResponseEntity<Void> forgotUserReCreatePassword(
            @RequestBody UserResetPasswordCommand userResetPasswordCommand) {
        userApplicationService.resetPasswordByEmail(userResetPasswordCommand);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable @RequestHeader("userId") UUID userId) {
        userApplicationService.deleteUser(UserDeleteCommand.builder()
                .userId(userId)
                .build());
        return ResponseEntity.noContent().build();
    }
}
