package com.jar.service.system.user.service.application;

import com.jar.service.system.user.service.application.dto.create.UserAuthenticationCommand;
import com.jar.service.system.user.service.application.dto.create.UserTokenResponse;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeGenerationCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeVerificationCommand;
import com.jar.service.system.user.service.application.handler.UserAuthenticationHandler;
import com.jar.service.system.user.service.application.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserAuthenticationHandler userAuthenticationHandler;

    public UserAuthenticationServiceImpl(UserAuthenticationHandler userAuthenticationHandler) {
        this.userAuthenticationHandler = userAuthenticationHandler;
    }

    @Override
    public void sendEmailVerificationCode(EmailCodeGenerationCommand emailCodeGenerationCommand) {
        userAuthenticationHandler.sendAuthenticationCode(emailCodeGenerationCommand);
    }

    @Override
    public void verifyCodeAndEnableAccount(EmailCodeVerificationCommand emailCodeVerificationCommand) {
        userAuthenticationHandler.verifyEmailCode(emailCodeVerificationCommand);
    }

    @Override
    public UserTokenResponse authenticate(UserAuthenticationCommand userAuthenticationCommand) {
        return userAuthenticationHandler.generateToken(userAuthenticationCommand);
    }

}
