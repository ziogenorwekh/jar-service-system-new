package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderContainerCreateApprovalPublisher;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderContainerCreatedEventPublisher;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderFailedPublisher;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.event.AppOrderContainerCreationApprovalEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderCreatedContainerEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderFailedEvent;
import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.BaseId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AppOrderMessageProcessor {

    private final AppOrderContainerCreateApprovalPublisher appOrderContainerCreateApprovalPublisher;
    private final AppOrderFailedPublisher appOrderFailedPublisher;

    private final AppOrderContainerCreatedEventPublisher appOrderContainerCreatedEventPublisher;

    private final AppOrderContainerMessageHelper appOrderContainerMessageHelper;
    private final AppOrderStorageMessageHelper appOrderStorageMessageHelper;

    @Autowired
    public AppOrderMessageProcessor(AppOrderContainerCreateApprovalPublisher appOrderContainerCreateApprovalPublisher,
                                    AppOrderFailedPublisher appOrderFailedPublisher,
                                    AppOrderContainerCreatedEventPublisher appOrderContainerCreatedEventPublisher,
                                    AppOrderContainerMessageHelper appOrderContainerMessageHelper,
                                    AppOrderStorageMessageHelper appOrderStorageMessageHelper) {
        this.appOrderContainerCreateApprovalPublisher = appOrderContainerCreateApprovalPublisher;
        this.appOrderFailedPublisher = appOrderFailedPublisher;
        this.appOrderContainerCreatedEventPublisher = appOrderContainerCreatedEventPublisher;
        this.appOrderContainerMessageHelper = appOrderContainerMessageHelper;
        this.appOrderStorageMessageHelper = appOrderStorageMessageHelper;
    }

    public void createStorageStep(StorageApprovalResponse storageApprovalResponse) {
        log.trace("Processing Storage Approval Response for id: {}", storageApprovalResponse.getAppOrderId());

        AppOrderEvent<? extends AggregateRoot<? extends BaseId<UUID>>> appOrderEvent =
                appOrderStorageMessageHelper.saveStorageHelper(storageApprovalResponse);

        if (appOrderEvent instanceof AppOrderFailedEvent appOrderFailedEvent) {
            appOrderFailedPublisher.publish(appOrderFailedEvent);
        }
        else if (appOrderEvent instanceof AppOrderContainerCreationApprovalEvent
                appOrderContainerCreationApprovalEvent) {
            appOrderContainerCreateApprovalPublisher.publish(appOrderContainerCreationApprovalEvent);
        }
    }

    /**
     * Container Approval Response 처리
     */
    public void createContainerStep(ContainerApprovalResponse containerApprovalResponse) {
        log.trace("Processing Container Approval Response for id: {}", containerApprovalResponse.getAppOrderId());

        AppOrderEvent<AppOrder> appOrderAppOrderEvent =
                appOrderContainerMessageHelper.saveContainer(containerApprovalResponse);

        if (appOrderAppOrderEvent instanceof AppOrderCreatedContainerEvent appOrderCreatedContainerEvent) {
            appOrderContainerCreatedEventPublisher.publish(appOrderCreatedContainerEvent);
        } else if (appOrderAppOrderEvent instanceof AppOrderFailedEvent appOrderFailedEvent) {
            appOrderFailedPublisher.publish(appOrderFailedEvent);
        }
    }

    /**
     * 처리 실패 시 예외 처리
     */
    public void failureStep(Object approvalResponse) {
        AppOrderFailedEvent appOrderFailedEvent = null;
        if (approvalResponse instanceof StorageApprovalResponse response) {
            appOrderFailedEvent = appOrderStorageMessageHelper.rejectProcessingEvent(
                    appOrderStorageMessageHelper.findAppOrder(response.getAppOrderId()), response.getError());
        } else if (approvalResponse instanceof ContainerApprovalResponse response) {
            appOrderContainerMessageHelper.rejectProcessingEvent(
                    appOrderContainerMessageHelper.findAppOrder(response.getAppOrderId()),response.getError());
        } else {
            throw new IllegalArgumentException("Invalid approval response type");
        }
        appOrderFailedPublisher.publish(appOrderFailedEvent);
    }

}
