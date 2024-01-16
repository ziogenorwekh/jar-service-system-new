package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.ports.output.publisher.*;
import com.jar.service.system.apporder.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.AppOrderDomainServiceImpl;
import com.jar.service.system.apporder.service.application.handler.AppOrderCreateHandler;
import com.jar.service.system.apporder.service.application.handler.AppOrderDeleteHandler;
import com.jar.service.system.apporder.service.application.handler.AppOrderTrackQueryHandler;
import com.jar.service.system.apporder.service.application.mapper.AppOrderDataMapper;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAppOrderConfiguration {


    @Bean
    public AppOrderDomainService appOrderDomainService() {
        return new AppOrderDomainServiceImpl();
    }

    @Bean
    public AppOrderApplicationService appOrderApplicationService() {
        return new AppOrderApplicationServiceImpl(appOrderCreateHandler(),
                appOrderTrackQueryHandler(), appOrderDeleteHandler());
    }

    @Bean
    public AppOrderDeleteHandler appOrderDeleteHandler() {
        return new AppOrderDeleteHandler(appOrderRepository(), appOrderDeleteApprovalPublisher(),
                appOrderDomainService(), appOrderDataMapper(),
                containerRepository(), storageRepository(), userRepository());
    }

    @Bean
    public StorageRepository storageRepository() {
        return Mockito.mock(StorageRepository.class);
    }

    @Bean
    public ContainerRepository containerRepository() {
        return Mockito.mock(ContainerRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public AppOrderDeleteApprovalPublisher appOrderDeleteApprovalPublisher() {
        return Mockito.mock(AppOrderDeleteApprovalPublisher.class);
    }

    @Bean
    public AppOrderDataMapper appOrderDataObjectMapper() {
        return new AppOrderDataMapper();
    }


    @Bean
    public AppOrderDataMapper appOrderDataMapper() {
        return new AppOrderDataMapper();
    }

    @Bean
    public AppOrderCreateHandler appOrderCreateHandler() {
        return new AppOrderCreateHandler(appOrderDataMapper(),
                appOrderRepository(), appOrderDomainService(), appOrderCreatedPublisher(), userRepository());
    }

    @Bean
    public AppOrderFailedPublisher appOrderFailedPublisher() {
        return Mockito.mock(AppOrderFailedPublisher.class);
    }

    @Bean
    public AppOrderMessageProcessor appOrderMessageProcessor() {
        return new AppOrderMessageProcessor(
                appOrderContainerMessageHelper(), appOrderStorageMessageHelper());
    }

    @Bean
    public AppOrderContainerMessageHelper appOrderContainerMessageHelper() {
        return new AppOrderContainerMessageHelper(appOrderDomainService(),
                appOrderRepository(), appOrderDataMapper(), containerRepository(),appOrderFailedPublisher());
    }

    @Bean
    public AppOrderStorageMessageHelper appOrderStorageMessageHelper() {
        return new AppOrderStorageMessageHelper(appOrderDomainService(),
                appOrderRepository(), appOrderDataMapper(), storageRepository()
                , containerRepository(), appOrderContainerCreateApprovalPublisher(), appOrderFailedPublisher());
    }


//    @Bean
//    public AppOrderMessageHelper appOrderMessageHandler() {
//        return new AppOrderMessageHelper(appOrderDomainService(),appOrderRepository());
//    }

    @Bean
    public AppOrderContainerCreateApprovalPublisher appOrderContainerCreateApprovalPublisher() {
        return Mockito.mock(AppOrderContainerCreateApprovalPublisher.class);
    }


    @Bean
    public AppOrderContainerCreateApprovalPublisher appOrderContainerCreatedPublisher() {
        return Mockito.mock(AppOrderContainerCreateApprovalPublisher.class);
    }

    @Bean
    public AppOrderCreatedPublisher appOrderCreatedPublisher() {
        return Mockito.mock(AppOrderCreatedPublisher.class);
    }

    @Bean
    public AppOrderRepository appOrderRepository() {
        return Mockito.mock(AppOrderRepository.class);
    }

    @Bean
    public AppOrderTrackQueryHandler appOrderTrackQueryHandler() {
        return new AppOrderTrackQueryHandler(appOrderRepository(), appOrderDataMapper());
    }

//    @Bean
//    public StorageRepository storageRepository() {
//        return Mockito.mock(StorageRepository.class);
//    }
//
//    @Bean
//    public ContainerRepository containerRepository() {
//        return Mockito.mock(ContainerRepository.class);
//    }
//
//    @Bean
//    public UserRepository userRepository() {
//        return Mockito.mock(UserRepository.class);
//    }

    @Bean
    public AppOrderCreatedPublisher kafkaAppOrderProducer() {
        return Mockito.mock(AppOrderCreatedPublisher.class);
    }


    @Bean
    public AppOrderContainerCreateApprovalPublisher kafkaAppOrderContainerCreateProducer() {
        return Mockito.mock(AppOrderContainerCreateApprovalPublisher.class);
    }
}
