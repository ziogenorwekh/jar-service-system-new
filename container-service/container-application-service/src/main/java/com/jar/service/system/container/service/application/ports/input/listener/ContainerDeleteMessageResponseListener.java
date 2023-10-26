package com.jar.service.system.container.service.application.ports.input.listener;

import com.jar.service.system.container.service.application.dto.message.ContainerDeleteApprovalResponse;

public interface ContainerDeleteMessageResponseListener {

    void deleteContainerApproval(ContainerDeleteApprovalResponse containerDeleteApprovalResponse);
}
