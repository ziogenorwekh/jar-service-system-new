package com.jar.service.system.user.service.application.handler;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.user.service.application.dto.track.TrackUserResponse;
import com.jar.service.system.user.service.application.exception.UserNotFoundException;
import com.jar.service.system.user.service.application.mapper.UserDataMapper;
import com.jar.service.system.user.service.application.ports.output.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserTrackCommandHandler {

    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;

    @Autowired
    public UserTrackCommandHandler(UserRepository userRepository,
                                   UserDataMapper userDataMapper) {
        this.userRepository = userRepository;
        this.userDataMapper = userDataMapper;
    }

    @Transactional(readOnly = true)
    public TrackUserResponse trackUser(TrackUserQuery trackUserQuery) {
        User user = userRepository.findById(new UserId(trackUserQuery.getUserId()))
                .orElseThrow(() -> new UserNotFoundException(String.format("user not found by id : %s"
                        , trackUserQuery.getUserId())));

        return userDataMapper.convertUserToTrackUserResponse(user);
    }
}
