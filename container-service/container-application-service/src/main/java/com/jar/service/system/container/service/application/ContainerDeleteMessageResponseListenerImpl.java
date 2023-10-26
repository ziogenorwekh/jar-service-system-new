package com.jar.service.system.container.service.application;

import com.jar.service.system.container.service.application.dto.message.ContainerDeleteApprovalResponse;
import com.jar.service.system.container.service.application.handler.ContainerDeleteHandler;
import com.jar.service.system.container.service.application.ports.input.listener.ContainerDeleteMessageResponseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerDeleteMessageResponseListenerImpl implements ContainerDeleteMessageResponseListener {

    private final ContainerDeleteHandler containerDeleteHandler;

    @Autowired
    public ContainerDeleteMessageResponseListenerImpl(ContainerDeleteHandler containerDeleteHandler) {
        this.containerDeleteHandler = containerDeleteHandler;
    }

    @Override
    public void deleteContainerApproval(ContainerDeleteApprovalResponse containerDeleteApprovalResponse) {
        containerDeleteHandler.deleteContainerByMessage(containerDeleteApprovalResponse);
    }
}
