package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderFailedPublisher;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.event.AppOrderFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppOrderMessageProcessor {

    private final AppOrderContainerMessageHelper appOrderContainerMessageHelper;
    private final AppOrderStorageMessageHelper appOrderStorageMessageHelper;

    @Autowired
    public AppOrderMessageProcessor(AppOrderContainerMessageHelper appOrderContainerMessageHelper,
                                    AppOrderStorageMessageHelper appOrderStorageMessageHelper) {
        this.appOrderContainerMessageHelper = appOrderContainerMessageHelper;
        this.appOrderStorageMessageHelper = appOrderStorageMessageHelper;
    }

    public void storageStep(StorageApprovalResponse storageApprovalResponse) {
        log.trace("Processing Storage Approval Response for id: {}", storageApprovalResponse.getAppOrderId());
        appOrderStorageMessageHelper.storageProcessing(storageApprovalResponse);

//        if (appOrderEvent instanceof AppOrderFailedEvent appOrderFailedEvent) {
//            appOrderFailedPublisher.publish(appOrderFailedEvent);
//        } else if (appOrderEvent instanceof AppOrderContainerCreationApprovalEvent
//                appOrderContainerCreationApprovalEvent) {
//            Container container = appOrderContainerCreationApprovalEvent.getDomainType();
//            appOrderContainerMessageHelper.saveContainer(container);
//            appOrderContainerCreateApprovalPublisher.publish(appOrderContainerCreationApprovalEvent);
//        }
    }


    public void containerStep(ContainerApprovalResponse containerApprovalResponse) {
        log.trace("Processing Container Approval Response for id: {}", containerApprovalResponse.getAppOrderId());
        appOrderContainerMessageHelper.containerProcessing(containerApprovalResponse);

//        if (appOrderAppOrderEvent instanceof AppOrderCreatedContainerEvent appOrderCreatedContainerEvent) {
//            appOrderContainerCreatedEventPublisher.publish(appOrderCreatedContainerEvent);
//        } else if (appOrderAppOrderEvent instanceof AppOrderFailedEvent appOrderFailedEvent) {
//            appOrderFailedPublisher.publish(appOrderFailedEvent);
//        }
    }

    /**
     * 처리 실패 시 예외 처리
     */


    public void storageApprovalFailureStep(StorageApprovalResponse storageApprovalResponse) {
        appOrderStorageMessageHelper.failureInitializeContainer(storageApprovalResponse);
    }

    public void containerApprovalFailureStep(ContainerApprovalResponse containerApprovalResponse) {
        appOrderStorageMessageHelper.failureContainerizingContainer(containerApprovalResponse);
    }

//    public void failureStep(Object approvalResponse) {
//        if (approvalResponse.getClass().equals(StorageApprovalResponse.class)) {
//            appOrderStorageMessageHelper.failureEvent(appOrderStorageMessageHelper.
//                    findAppOrder(((StorageApprovalResponse) approvalResponse).getAppOrderId(),
//                            ((StorageApprovalResponse) approvalResponse).getError()));
//        } else {
//
//        }

//        AppOrderFailedEvent appOrderFailedEvent = null;
//        if (approvalResponse instanceof StorageApprovalResponse response) {
//            log.trace("approvalResponse is StorageApprovalResponse");
//            appOrderFailedEvent = appOrderStorageMessageHelper.failureProcessing(
//                    appOrderStorageMessageHelper.findAppOrder(response.getAppOrderId()), response.getError());
//        } else if (approvalResponse instanceof ContainerApprovalResponse response) {
//            log.trace("approvalResponse is ContainerApprovalResponse");
//            appOrderFailedEvent = appOrderContainerMessageHelper.failureProcessing(
//                    appOrderContainerMessageHelper.findAppOrder(response.getAppOrderId()), response.getError());
//            AppOrder appOrder = appOrderFailedEvent.getDomainType();
//            appOrderStorageMessageHelper.deleteStorage(appOrder);
//            appOrderContainerMessageHelper.deleteContainer(appOrder);
//        } else {
//            throw new IllegalArgumentException("Invalid approval response type");
//        }
//        appOrderFailedPublisher.publish(appOrderFailedEvent);
//    }

}
