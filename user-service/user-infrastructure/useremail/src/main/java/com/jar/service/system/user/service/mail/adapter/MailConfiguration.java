package com.jar.service.system.user.service.mail.adapter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

    private final MailConfigData mailConfigData;

    public MailConfiguration(MailConfigData mailConfigData) {
        this.mailConfigData = mailConfigData;
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(mailConfigData.getProperties());
        javaMailSender.setHost(mailConfigData.getHost());
        javaMailSender.setUsername(mailConfigData.getUsername());
        javaMailSender.setPassword(mailConfigData.getPassword());
        javaMailSender.setPort(mailConfigData.getPort());
        return javaMailSender;
    }
}
