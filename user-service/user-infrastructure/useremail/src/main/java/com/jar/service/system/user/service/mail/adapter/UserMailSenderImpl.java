package com.jar.service.system.user.service.mail.adapter;

import com.jar.service.system.user.service.application.ports.output.mail.UserMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserMailSenderImpl implements UserMailSender {

    private final MailSender mailSender;

    @Autowired
    public UserMailSenderImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void sendVerifyCode(Integer verifyCode, String targetEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Email Verification Code");
        simpleMailMessage.setText("Your verification code is: " + verifyCode);
        try {
            mailSender.send(simpleMailMessage);
            log.info("Email sent successfully.");
        } catch (MailException e) {
            log.error("Failed to send email: {}", e.getMessage());
        }
    }

    @Async
    @Override
    public void sendResetPassword(String newPwd, String targetEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Here is your new Password.");
        simpleMailMessage.setText("Your new Password is: " + newPwd);
        try {
            mailSender.send(simpleMailMessage);
            log.info("Email sent successfully.");
        } catch (MailException e) {
            log.error("Failed to send email: {}", e.getMessage());
        }
    }
}
