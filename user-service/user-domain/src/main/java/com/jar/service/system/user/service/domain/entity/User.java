package com.jar.service.system.user.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.DatabaseId;
import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.user.service.domain.exception.UserDomainException;
import com.jar.service.system.user.service.domain.valueobject.ChangePassword;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
public class User extends AggregateRoot<UserId> {

    private final String username;
    private final String email;
    private String password;
    private String rawPassword;

    private Boolean userActive;

    private Integer verifyEmailCode;

    @Builder
    private User(String username, String password, String email, String rawPassword,
                 Boolean userActive, UserId userId, Integer verifyEmailCode) {
        super.setId(userId);
        this.username = username;
        this.password = password;
        this.email = email;
        this.rawPassword = rawPassword;
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

    /**
     * currentInputPassword do check now rawPassword.
     * encryptedPassword is new encoded Encrypted password.
     *
     * @param changePassword value object
     */
    public void updatePassword(ChangePassword changePassword) {
        if (!Objects.equals(this.rawPassword, changePassword.getCurrentRawPassword())) {
            throw new UserDomainException("currentInputPassword is not matching input password.");
        }
        this.rawPassword = changePassword.getNewRawPassword();
        this.password = changePassword.getNewEncryptedPassword();
    }

    public void resetPassword(ChangePassword changePassword) {
        this.rawPassword = changePassword.getNewRawPassword();
        this.password = changePassword.getNewEncryptedPassword();
    }

    private void initializeEmailCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(10);
            code.append(number);
        }
        this.verifyEmailCode = Integer.parseInt(code.toString());
    }

}
