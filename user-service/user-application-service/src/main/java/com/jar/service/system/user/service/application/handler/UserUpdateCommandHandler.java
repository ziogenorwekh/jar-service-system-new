package com.jar.service.system.user.service.application.handler;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.application.dto.update.UserResetPasswordCommand;
import com.jar.service.system.user.service.application.exception.UserUnAuthenticationException;
import com.jar.service.system.user.service.application.ports.output.mail.UserMailSender;
import com.jar.service.system.user.service.domain.UserDomainService;
import com.jar.service.system.user.service.domain.entity.User;
import com.jar.service.system.user.service.application.dto.update.UserUpdateCommand;
import com.jar.service.system.user.service.application.exception.UserNotFoundException;
import com.jar.service.system.user.service.application.mapper.UserDataMapper;
import com.jar.service.system.user.service.application.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class UserUpdateCommandHandler {


    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserDomainService userDomainService;

    private final UserMailSender userMailSender;

    public UserUpdateCommandHandler(UserRepository userRepository, UserDataMapper userDataMapper,
                                    PasswordEncoder passwordEncoder, UserDomainService userDomainService,
                                    UserMailSender userMailSender) {
        this.userRepository = userRepository;
        this.userDataMapper = userDataMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDomainService = userDomainService;
        this.userMailSender = userMailSender;
    }


    @Transactional
    public void updateUserPassword(UserUpdateCommand userUpdateCommand) {
        String encodeNewPassword = passwordEncoder.encode(userUpdateCommand.getNewPassword());
        User user = findUserByRepository(userUpdateCommand.getUserId());
        log.info("update pwd before user's pwd : {}", user.getRawPassword());

        userDomainService.updateUserPassword(user, userDataMapper.
                convertUserUpdateCommandToChangePassword(userUpdateCommand, encodeNewPassword));
        userRepository.save(user);
        log.info("update pwd after user's pwd : {}", user.getRawPassword());
    }

    @Transactional
    public void resetPasswordByEmail(UserResetPasswordCommand userResetPasswordCommand) {
        User user = userRepository.findByEmail(userResetPasswordCommand.getEmail()).orElseThrow(() -> {
            throw new UserNotFoundException(String
                    .format("%s is not found.", userResetPasswordCommand.getEmail()));
        });
        if (!user.getUserActive()) {
            throw new UserUnAuthenticationException(String.format("%s is not authorized.", user.getEmail()));
        }
        log.trace("{} user update reset password.", user.getEmail());
        String newPwd = this.randomUserPwd();
        String newEncodedPwd = passwordEncoder.encode(newPwd);
        userDomainService.resetPassword(user, userDataMapper.convertChangePassword(newPwd, newEncodedPwd));
        User saved = userRepository.save(user);
        userMailSender.sendResetPassword(saved.getRawPassword(), saved.getEmail());
    }

    private User findUserByRepository(UUID userId) {
        return userRepository.findById(new UserId(userId))
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "user not found by id : %s", userId
                )));
    }

    private String randomUserPwd() {
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";

        StringBuilder randomPwd = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(charset.length());
            char randomChar = charset.charAt(index);
            randomPwd.append(randomChar);
        }
        return randomPwd.toString();
    }
}
