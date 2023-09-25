package com.jar.service.system.user.service.appilcation.api;

import com.jar.service.system.user.service.application.UserAuthenticationService;
import com.jar.service.system.user.service.application.dto.create.UserAuthenticationCommand;
import com.jar.service.system.user.service.application.dto.create.UserTokenResponse;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeGenerationCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeVerificationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class SecurityResource {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public SecurityResource(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserTokenResponse> loginUser(
            @RequestBody UserAuthenticationCommand userAuthenticationCommand) {
        UserTokenResponse userTokenResponse = userAuthenticationService
                .authenticate(userAuthenticationCommand);
        return ResponseEntity.ok().body(userTokenResponse);
    }

    @RequestMapping(value = "/mails", method = RequestMethod.POST)
    public ResponseEntity<Void> sendEmailVerificationCode(
            @RequestBody EmailCodeGenerationCommand emailCodeGenerationCommand) {
        userAuthenticationService.sendEmailVerificationCode(emailCodeGenerationCommand);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/mails", method = RequestMethod.PUT)
    public ResponseEntity<Void> verifyEmailVerificationCode(
            @RequestBody EmailCodeVerificationCommand emailCodeVerificationCommand) {
        userAuthenticationService.verifyCodeAndEnableAccount(emailCodeVerificationCommand);
        return ResponseEntity.accepted().build();
    }

}
