package com.jar.service.system.container.service.application;

import com.jar.service.system.container.service.application.dto.message.ContainerCreationApprovalResponse;
import com.jar.service.system.container.service.application.handler.ContainerCreateHandler;
import com.jar.service.system.container.service.application.ports.input.listener.ContainerCreationMessageResponseListener;
import com.jar.service.system.container.service.application.ports.output.publisher.ContainerEventPublisher;
import com.jar.service.system.container.service.domian.event.ContainerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerCreationMessageResponseListenerImpl implements ContainerCreationMessageResponseListener {

    private final ContainerCreateHandler containerCreateHandler;
    private final ContainerEventPublisher containerEventPublisher;

    @Autowired
    public ContainerCreationMessageResponseListenerImpl(ContainerCreateHandler containerCreateHandler,
                                                        ContainerEventPublisher containerEventPublisher) {
        this.containerCreateHandler = containerCreateHandler;
        this.containerEventPublisher = containerEventPublisher;
    }

    @Override
    public void createContainerApproval(ContainerCreationApprovalResponse containerCreationApprovalResponse) {
        ContainerEvent containerDomainEvent = containerCreateHandler
                .saveContainer(containerCreationApprovalResponse);
        containerEventPublisher.publish(containerDomainEvent);
    }
}
