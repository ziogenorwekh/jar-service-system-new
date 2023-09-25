package com.jar.service.system.container.service.application.ports.input.listener;

import com.jar.service.system.container.service.application.dto.message.ContainerCreationApprovalResponse;

public interface ContainerCreationMessageResponseListener {

    void createContainerApproval(ContainerCreationApprovalResponse containerCreationApprovalResponse);
}
