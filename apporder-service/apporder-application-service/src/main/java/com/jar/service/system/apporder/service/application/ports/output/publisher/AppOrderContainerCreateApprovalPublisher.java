package com.jar.service.system.apporder.service.application.ports.output.publisher;

import com.jar.service.system.apporder.service.domain.event.AppOrderContainerCreationApprovalEvent;
import com.jar.service.system.common.domain.event.DomainEventPublisher;
import org.springframework.stereotype.Component;

public interface AppOrderContainerCreateApprovalPublisher extends DomainEventPublisher<AppOrderContainerCreationApprovalEvent> {

}
