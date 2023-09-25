package com.jar.service.system.user.service.application.handler;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.domain.UserDomainService;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.application.dto.update.UserUpdateCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeVerificationCommand;
import com.jar.service.system.user.service.application.exception.UserNotFoundException;
import com.jar.service.system.user.service.application.mapper.UserDataMapper;
import com.jar.service.system.user.service.application.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class UserUpdateCommandHandler {


    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserDomainService userDomainService;

    public UserUpdateCommandHandler(UserRepository userRepository, UserDataMapper userDataMapper,
                                    PasswordEncoder passwordEncoder, UserDomainService userDomainService) {
        this.userRepository = userRepository;
        this.userDataMapper = userDataMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDomainService = userDomainService;
    }


    @Transactional
    public void updateUserPassword(UserUpdateCommand userUpdateCommand) {
        String encodePassword = passwordEncoder.encode(userUpdateCommand.getRawPassword());
        User user = findUserByRepository(userUpdateCommand.getUserId());
        log.info("update pwd before user's pwd : {}", user.getRawPassword());
        userDomainService.updateUserPassword(user, userDataMapper.
                convertUserUpdateCommandToChangePassword(userUpdateCommand, encodePassword));
        userRepository.save(user);
        log.info("update pwd after user's pwd : {}", user.getRawPassword());
    }

    private User findUserByRepository(UUID userId) {
        return userRepository.findById(new UserId(userId))
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "user not found by id : %s", userId
                )));
    }
}
