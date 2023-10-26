package com.jar.service.system.user.service.application.ports.output.mail;

public interface UserMailSender {

    void sendVerifyCode(Integer verifyCode, String targetEmail);

    void sendResetPassword(String newPwd, String targetEmail);
}
