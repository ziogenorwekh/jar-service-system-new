package com.jar.service.system.user.service.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePassword {

    private final String currentRawPassword;
    private final String newRawPassword;
    private final String newEncryptedPassword;

    @Builder
    private UpdatePassword(String currentRawPassword, String newRawPassword, String newEncryptedPassword) {
        this.currentRawPassword = currentRawPassword;
        this.newRawPassword = newRawPassword;
        this.newEncryptedPassword = newEncryptedPassword;
    }
}
