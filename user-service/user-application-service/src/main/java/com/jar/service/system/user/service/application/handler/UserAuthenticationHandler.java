package com.jar.service.system.user.service.application.handler;

import com.jar.service.system.user.service.application.dto.create.UserAuthenticationCommand;
import com.jar.service.system.user.service.application.dto.create.UserTokenResponse;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeGenerationCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeVerificationCommand;
import com.jar.service.system.user.service.application.exception.UserNotFoundException;
import com.jar.service.system.user.service.application.mapper.UserDataMapper;
import com.jar.service.system.user.service.application.ports.output.mail.UserMailSender;
import com.jar.service.system.user.service.application.security.CustomUserDetails;
import com.jar.service.system.user.service.application.security.CustomUserDetailsService;
import com.jar.service.system.user.service.application.security.jwt.JsonWebToken;
import com.jar.service.system.user.service.domain.UserDomainService;
import com.jar.service.system.user.service.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class UserAuthenticationHandler {


    private final CustomUserDetailsService customUserDetailsService;
    private final UserDataMapper userDataMapper;
    private final UserMailSender userMailSender;
    private final UserDomainService userDomainService;
    private final AuthenticationManager authenticationManager;
    private final JsonWebToken jsonWebToken;

    @Autowired
    public UserAuthenticationHandler(
            CustomUserDetailsService customUserDetailsService, UserDataMapper userDataMapper,
            UserMailSender userMailSender,
            UserDomainService userDomainService,
            AuthenticationManager authenticationManager,
            JsonWebToken jsonWebToken) {
        this.customUserDetailsService = customUserDetailsService;
        this.userDataMapper = userDataMapper;
        this.userMailSender = userMailSender;
        this.userDomainService = userDomainService;
        this.authenticationManager = authenticationManager;
        this.jsonWebToken = jsonWebToken;
    }

    @Transactional(readOnly = true)
    public void sendAuthenticationCode(EmailCodeGenerationCommand emailCodeGenerationCommand) {
        User user = customUserDetailsService.findByEmail(emailCodeGenerationCommand.getEmail());
        log.info("{} requests send verification code is : {}", user.getEmail(), user.getVerifyEmailCode());
        userMailSender.sendVerifyCode(user.getVerifyEmailCode(), user.getEmail());
    }

    @Transactional
    public void verifyEmailCode(EmailCodeVerificationCommand emailCodeVerificationCommand) {
        User user = customUserDetailsService.findByEmail(emailCodeVerificationCommand.getEmail());
        userDomainService
                .verifyUserEmailAddress(user, emailCodeVerificationCommand.getEmailCode());
        customUserDetailsService.save(user);
    }

    @Transactional(readOnly = true)
    public UserTokenResponse generateToken(UserAuthenticationCommand userAuthenticationCommand) {
        UsernamePasswordAuthenticationToken token;
        CustomUserDetails userDetails;
        try {
            token = new UsernamePasswordAuthenticationToken(userAuthenticationCommand.getEmail(),
                    userAuthenticationCommand.getPassword());
            Authentication authenticate = authenticationManager.authenticate(token);
            userDetails = (CustomUserDetails) authenticate.getPrincipal();
            if (!userDetails.isEnabled()) {
                throw new DisabledException("The user email has not been verified. Please first do main verify.");
            }
            return userDataMapper
                    .convertTokenToUserTokenResponse(jsonWebToken.generateToken(userDetails),
                            userDetails.getUserId());
        } catch (BadCredentialsException e) {
            log.error("error message : {}", e.getMessage());
            throw new BadCredentialsException("invalidate password.");
        } catch (UsernameNotFoundException e) {
            log.error("error message : {}", e.getMessage());
            throw new UserNotFoundException("User not found.");
        }
    }

}
