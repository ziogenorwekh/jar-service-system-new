package com.jar.service.system.user.service.application;

import com.jar.service.system.user.service.application.dto.create.UserAuthenticationCommand;
import com.jar.service.system.user.service.application.dto.create.UserTokenResponse;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeGenerationCommand;
import com.jar.service.system.user.service.application.dto.verify.EmailCodeVerificationCommand;
import jakarta.validation.Valid;

public interface UserAuthenticationService {

    void sendEmailVerificationCode(@Valid EmailCodeGenerationCommand emailCodeGenerationCommand);

    void verifyCodeAndEnableAccount(@Valid EmailCodeVerificationCommand emailCodeVerificationCommand);

    UserTokenResponse authenticate(@Valid UserAuthenticationCommand userAuthenticationCommand);

}
