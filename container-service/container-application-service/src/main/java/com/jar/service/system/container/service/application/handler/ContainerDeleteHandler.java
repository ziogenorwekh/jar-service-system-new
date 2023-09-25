package com.jar.service.system.container.service.application.handler;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.container.service.application.dto.message.ContainerDeleteApprovalResponse;
import com.jar.service.system.container.service.application.ports.output.dockeraccess.InstanceDockerAccess;
import com.jar.service.system.container.service.application.ports.output.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ContainerDeleteHandler {

    private final ContainerRepository containerRepository;
    private final InstanceDockerAccess instanceDockerAccess;

    @Autowired
    public ContainerDeleteHandler(ContainerRepository containerRepository,
                                  InstanceDockerAccess instanceDockerAccess) {
        this.containerRepository = containerRepository;
        this.instanceDockerAccess = instanceDockerAccess;
    }

    @Transactional
    public void deleteContainerByMessage(ContainerDeleteApprovalResponse containerDeleteApprovalResponse) {
        AppOrderId appOrderId = new AppOrderId(containerDeleteApprovalResponse.getAppOrderId());
        containerRepository.findByAppOrderId(appOrderId).ifPresent(container -> {
            instanceDockerAccess.deleteDockerContainer(container);
            containerRepository.delete(container);
        });
    }
}
