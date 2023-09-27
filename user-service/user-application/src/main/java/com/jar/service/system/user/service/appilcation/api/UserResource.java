package com.jar.service.system.user.service.appilcation.api;


import com.jar.service.system.user.service.application.UserApplicationService;
import com.jar.service.system.user.service.application.dto.create.UserCreateCommand;
import com.jar.service.system.user.service.application.dto.create.UserCreateResponse;
import com.jar.service.system.user.service.application.dto.delete.UserDeleteCommand;
import com.jar.service.system.user.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.user.service.application.dto.track.TrackUserResponse;
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
    public ResponseEntity<TrackUserResponse> retrieveUser(@PathVariable UUID userId) {
        TrackUserResponse trackUserResponse = userApplicationService
                .trackUser(TrackUserQuery.builder().userId(userId).build());
        return ResponseEntity.ok(trackUserResponse);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId,
                                           @RequestBody String rawPassword) {

        userApplicationService.updateUserPassword(UserUpdateCommand.builder().userId(userId)
                .rawPassword(rawPassword).build());
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userApplicationService.deleteUser(UserDeleteCommand.builder()
                .userId(userId)
                .build());
        return ResponseEntity.noContent().build();
    }
}