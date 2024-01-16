package com.jar.service.system.user.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.domain.exception.UserDomainException;
import com.jar.service.system.user.service.domain.valueobject.Password;
import com.jar.service.system.user.service.domain.valueobject.UpdatePassword;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
public class User extends AggregateRoot<UserId> {

    private final String username;
    private final String email;
    private Password password;
    private Password rawPassword;
    private Boolean userActive;
    private Integer verifyEmailCode;

    @Builder
    private User(String username, String password, String email, String rawPassword,
                 Boolean userActive, UserId userId, Integer verifyEmailCode) {
        super.setId(userId);
        this.username = username;
        this.password = new Password(password);
        this.email = email;
        this.rawPassword = new Password(rawPassword);
        this.userActive = userActive;
        this.verifyEmailCode = verifyEmailCode;
    }

    public void initializeUser() {
        super.setId(new UserId(UUID.randomUUID()));
        this.userActive = false;
        initializeEmailCode();
    }

    public void checkEmailAuthentication(Integer verifyEmailCode) {
        if (this.userActive) {
            throw new UserDomainException("User already verified.");
        }
        if (!verifyEmailCode.equals(this.verifyEmailCode)) {
            throw new UserDomainException("user verify code is not matched.");
        }
        this.userActive = true;
    }

    public void isUserActive() {
        if (this.userActive) {
            log.error("{} is already verified.", this.email);
            throw new UserDomainException(String.format("%s is already verified.", this.email));
        }
    }

    /**
     * currentInputPassword do check now rawPassword.
     * encryptedPassword is new encoded Encrypted password.
     *
     * @param updatePassword value object
     */
    public void updatePassword(UpdatePassword updatePassword) {
        if (this.password.match(updatePassword.getCurrentRawPassword())) {
            throw new UserDomainException("currentInputPassword is not matching input password.");
        }
        this.rawPassword = new Password(updatePassword.getNewRawPassword());
        this.password = new Password(updatePassword.getNewEncryptedPassword());
    }

    public void resetPassword(UpdatePassword updatePassword) {
        this.rawPassword = new Password(updatePassword.getNewRawPassword());
        this.password = new Password(updatePassword.getNewEncryptedPassword());
    }

    private void initializeEmailCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(888888) + 111111;
            code = String.valueOf(number);
        }
        this.verifyEmailCode = Integer.parseInt(code);
    }

}
