package com.jar.service.system.user.service.mail.adapter;

import com.jar.service.system.user.service.application.ports.output.mail.UserAuthenticateMailSender;
import com.sun.management.GarbageCollectionNotificationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserAuthenticateMailSenderImpl implements UserAuthenticateMailSender {

    private final MailSender mailSender;

    @Autowired
    public UserAuthenticateMailSenderImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(Integer verifyCode, String targetEmail) {
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
}
