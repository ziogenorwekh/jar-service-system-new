package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppOrderMessageProcessStep {

    private final AppOrderContainerMessageHelper appOrderContainerMessageHelper;
    private final AppOrderStorageMessageHelper appOrderStorageMessageHelper;

    @Autowired
    public AppOrderMessageProcessStep(AppOrderContainerMessageHelper appOrderContainerMessageHelper,
                                      AppOrderStorageMessageHelper appOrderStorageMessageHelper) {
        this.appOrderContainerMessageHelper = appOrderContainerMessageHelper;
        this.appOrderStorageMessageHelper = appOrderStorageMessageHelper;
    }

    public void storageStep(StorageApprovalResponse storageApprovalResponse) {
        log.trace("Processing Storage Approval Response for id: {}", storageApprovalResponse.getAppOrderId());
        appOrderStorageMessageHelper.processStorageMessage(storageApprovalResponse);

    }

    public void containerStep(ContainerApprovalResponse containerApprovalResponse) {
        log.trace("Processing Container Approval Response for id: {}", containerApprovalResponse.getAppOrderId());
        appOrderContainerMessageHelper.processContainerMessage(containerApprovalResponse);
    }

    public void storageApprovalFailureStep(StorageApprovalResponse storageApprovalResponse) {
        appOrderStorageMessageHelper.failureInitializeContainer(storageApprovalResponse);
    }

    public void containerApprovalFailureStep(ContainerApprovalResponse containerApprovalResponse) {
        appOrderStorageMessageHelper.failureContainerizingContainer(containerApprovalResponse);
    }
}
