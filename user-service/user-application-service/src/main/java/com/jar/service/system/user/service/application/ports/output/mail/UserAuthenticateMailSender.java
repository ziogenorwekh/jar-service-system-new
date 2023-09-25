package com.jar.service.system.user.service.application.ports.output.mail;

public interface UserAuthenticateMailSender {

    void send(Integer verifyCode, String targetEmail);
}
