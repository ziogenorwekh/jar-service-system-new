package com.jar.service.system.user.service.container;

import com.jar.service.system.user.service.domain.UserDomainService;
import com.jar.service.system.user.service.domain.UserDomainServiceImpl;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.io.HttpTransportMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.MXBean;

@Configuration
public class BeanConfiguration {


    @Bean
    public UserDomainService userDomainService() {
        return new UserDomainServiceImpl();
    }

}
