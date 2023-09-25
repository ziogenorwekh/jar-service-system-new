package com.jar.service.system.user.service.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangePassword {

    private final String currentRawPassword;
    private final String newEncryptedPassword;

    @Builder
    private ChangePassword(String currentRawPassword, String newEncryptedPassword) {
        this.currentRawPassword = currentRawPassword;
        this.newEncryptedPassword = newEncryptedPassword;
    }
}
