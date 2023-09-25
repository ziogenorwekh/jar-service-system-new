package com.jar.service.system.user.service.application.handler;

import com.jar.service.system.common.domain.exception.ApplicationException;
import com.jar.service.system.user.service.domain.UserDomainService;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.application.dto.create.UserCreateCommand;
import com.jar.service.system.user.service.application.dto.create.UserCreateResponse;
import com.jar.service.system.user.service.application.mapper.UserDataMapper;
import com.jar.service.system.user.service.application.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class UserCreateCommandHandler {

    private final PasswordEncoder passwordEncoder;
    private final UserDomainService userDomainService;

    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;


    @Autowired
    public UserCreateCommandHandler(PasswordEncoder passwordEncoder,
                                    UserDomainService userDomainService,
                                    UserRepository userRepository,
                                    UserDataMapper userDataMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userDomainService = userDomainService;
        this.userRepository = userRepository;
        this.userDataMapper = userDataMapper;
    }


    @Transactional
    public UserCreateResponse createUser(UserCreateCommand userCreateCommand) {
        validateDuplicatedUserEmail(userCreateCommand);
        log.info(userCreateCommand.getEmail());

        String encodePassword = passwordEncoder.encode(userCreateCommand.getPassword());
        User user = userDataMapper.convertUserToUserCreateCommand(userCreateCommand
                ,encodePassword);
        userDomainService.initializeUser(user);
        userRepository.save(user);
        return userDataMapper.convertUserCreateResponseToUser(user);
    }

    private void validateDuplicatedUserEmail(UserCreateCommand userCreateCommand) {
        userRepository.findByEmail(userCreateCommand.getEmail())
                .ifPresent(user -> {
                    throw new ApplicationException(String.format("already registered user's email : %s"
                            , user.getEmail()));
                });
    }

}
