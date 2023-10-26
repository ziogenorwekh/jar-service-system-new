package com.jar.service.system.apporder.service.container;

import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderContainerCreateApprovalPublisher;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.AppOrderDomainServiceImpl;
import com.jar.service.system.user.service.kafka.adapter.publisher.AppOrderContainerCreateApprovalPublisherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public AppOrderDomainService appOrderDomainService() {
        return new AppOrderDomainServiceImpl();
    }

}
