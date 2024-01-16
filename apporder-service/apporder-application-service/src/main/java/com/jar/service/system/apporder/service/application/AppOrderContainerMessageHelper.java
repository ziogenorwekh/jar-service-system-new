package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.mapper.AppOrderDataMapper;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderFailedPublisher;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.domain.event.AppOrderFailedEvent;
import com.jar.service.system.apporder.service.domain.valueobject.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class AppOrderContainerMessageHelper extends AppOrderMessageHelper {

    private final AppOrderDomainService appOrderDomainService;
    private final AppOrderRepository appOrderRepository;
    private final AppOrderDataMapper appOrderDataMapper;

    private final ContainerRepository containerRepository;


    private final AppOrderFailedPublisher appOrderFailedPublisher;
    @Value("${server.domain}")
    private String defaultDomain;

    @Autowired
    public AppOrderContainerMessageHelper(
            AppOrderDomainService appOrderDomainService,
            AppOrderRepository appOrderRepository,
            AppOrderDataMapper appOrderDataMapper,
            ContainerRepository containerRepository,
            AppOrderFailedPublisher appOrderFailedPublisher) {
        super(appOrderDomainService, appOrderRepository);
        this.appOrderDomainService = appOrderDomainService;
        this.appOrderRepository = appOrderRepository;
        this.appOrderDataMapper = appOrderDataMapper;
        this.containerRepository = containerRepository;
        this.appOrderFailedPublisher = appOrderFailedPublisher;
    }

    @Transactional
    public void processContainerMessage(ContainerApprovalResponse containerApprovalResponse) {
        AppOrder appOrder = findAppOrder(containerApprovalResponse.getAppOrderId());
        try {
            Container container = appOrderDataMapper
                    .convertContainerApprovalResponseToContainer(containerApprovalResponse);
//             dev
            ServerConfig serverConfig = appOrderDataMapper.convertContainerWithDomainToServerConfig(container,
                    String.format("http://%s.%s:%s", container.getApplicationName(), defaultDomain,
                            container.getServerPort()));

            // prod
//             ServerConfig serverConfig = appOrderDataMapper.convertContainerWithDomainToServerConfig(container,
//                     String.format("%s.%s", defaultDomain, container.getApplicationName()));

            appOrderDomainService.successfulCreationContainer(appOrder, container, serverConfig);

            log.trace("save Container is AppOrder data endPoint : {}", appOrder.getServerConfig().getEndPoint());
            appOrderRepository.save(appOrder);
            containerRepository.save(container);
        } catch (Exception e) {
            AppOrderFailedEvent appOrderFailedEvent = failureProcessStep(appOrder, e.getMessage());
            appOrderFailedPublisher.publish(appOrderFailedEvent);
        }
    }

}
