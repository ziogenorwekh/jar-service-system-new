package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.apporder.service.application.mapper.AppOrderDataMapper;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderContainerCreateApprovalPublisher;
import com.jar.service.system.apporder.service.application.ports.output.publisher.AppOrderFailedPublisher;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.ContainerRepository;
import com.jar.service.system.apporder.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.apporder.service.domain.AppOrderDomainService;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.domain.event.AppOrderContainerCreationApprovalEvent;
import com.jar.service.system.apporder.service.domain.event.AppOrderFailedEvent;
import com.jar.service.system.apporder.service.domain.valueobject.ContainerConfig;
import com.jar.service.system.common.domain.valueobject.StorageId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class AppOrderStorageMessageHelper extends AppOrderMessageHelper {

    private final AppOrderDomainService appOrderDomainService;
    private final AppOrderRepository appOrderRepository;
    private final AppOrderDataMapper appOrderDataMapper;
    private final StorageRepository storageRepository;

    private final ContainerRepository containerRepository;

    private final AppOrderContainerCreateApprovalPublisher appOrderContainerCreateApprovalPublisher;
    private final AppOrderFailedPublisher appOrderFailedPublisher;

    @Autowired
    public AppOrderStorageMessageHelper(
            AppOrderDomainService appOrderDomainService,
            AppOrderRepository appOrderRepository,
            AppOrderDataMapper appOrderDataMapper,
            StorageRepository storageRepository,
            ContainerRepository containerRepository,
            AppOrderContainerCreateApprovalPublisher appOrderContainerCreateApprovalPublisher,
            AppOrderFailedPublisher appOrderFailedPublisher) {
        super(appOrderDomainService, appOrderRepository);
        this.appOrderDomainService = appOrderDomainService;
        this.appOrderRepository = appOrderRepository;
        this.appOrderDataMapper = appOrderDataMapper;
        this.storageRepository = storageRepository;
        this.containerRepository = containerRepository;
        this.appOrderContainerCreateApprovalPublisher = appOrderContainerCreateApprovalPublisher;
        this.appOrderFailedPublisher = appOrderFailedPublisher;
    }

    @Transactional
    public void storageProcessing(
            StorageApprovalResponse storageApprovalResponse) {
        log.trace("StorageApprovalResponse : {}", storageApprovalResponse.toString());
        AppOrder appOrder = findAppOrder(storageApprovalResponse.getAppOrderId());
        try {
            Storage storage = appOrderDataMapper
                    .convertStorageApprovalResponseToStorage(storageApprovalResponse);
            appOrderDomainService.successfulCreationStorage(appOrder, storage);
            storageRepository.save(storage);
//            appOrderRepository.save(appOrder);
            initializeContainer(appOrder, storage);
        } catch (Exception e) {
            AppOrderFailedEvent appOrderFailedEvent = failureProcessing(appOrder, e.getMessage());
            appOrderFailedPublisher.publish(appOrderFailedEvent);
        }
    }

    private void initializeContainer(
            AppOrder appOrder, Storage storage) {

        ContainerConfig containerConfig = appOrderDataMapper.
                convertServerConfigToContainerConfig(appOrder.getServerConfig(), storage.getFileUrl());

        AppOrderContainerCreationApprovalEvent appOrderContainerCreationApprovalEvent =
                appOrderDomainService.initializeAppOrderContainer(appOrder, containerConfig);
        log.trace("save Container is AppOrder data endPoint : {}", appOrder.getServerConfig().getEndPoint());
        containerRepository.save(appOrderContainerCreationApprovalEvent.getDomainType());
        appOrderRepository.save(appOrder);
        appOrderContainerCreateApprovalPublisher.publish(appOrderContainerCreationApprovalEvent);
    }

    //    문제있음
    @Transactional
    public void failureInitializeContainer(StorageApprovalResponse storageApprovalResponse) {
        AppOrder appOrder = this.findAppOrder(storageApprovalResponse.getAppOrderId());

        AppOrderFailedEvent appOrderFailedEvent = failureProcessing(appOrder,
                storageApprovalResponse.getError());
        storageRepository.deleteByStorageId(appOrder.getStorageId());
        appOrderFailedPublisher.publish(appOrderFailedEvent);
    }

    @Transactional
    public void failureContainerizingContainer(ContainerApprovalResponse containerApprovalResponse) {
        AppOrder appOrder = this.findAppOrder(containerApprovalResponse.getAppOrderId());
        AppOrderFailedEvent appOrderFailedEvent = failureProcessing(appOrder, containerApprovalResponse.getError());
        storageRepository.deleteByStorageId(appOrder.getStorageId());
        containerRepository.deleteByContainerId(appOrder.getContainerId());
        appOrderFailedPublisher.publish(appOrderFailedEvent);
    }



    @Transactional
    public void deleteStorage(AppOrder appOrder) {
        storageRepository.deleteByStorageId(appOrder.getStorageId());
    }

}
