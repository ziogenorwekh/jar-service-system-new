package com.jar.service.system.storage.service.application.handler;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.storage.service.application.dto.delete.StorageDeleteCommand;
import com.jar.service.system.storage.service.application.dto.message.StorageDeleteApprovalResponse;
import com.jar.service.system.storage.service.application.exception.StorageNotFoundException;
import com.jar.service.system.storage.service.application.mapper.StorageDataMapper;
import com.jar.service.system.storage.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.storage.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.storage.service.application.ports.output.s3.AmazonS3Handler;
import com.jar.service.system.storage.service.domain.entity.AppOrder;
import com.jar.service.system.storage.service.domain.entity.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class StorageDeleteHandler {


    private final StorageRepository storageRepository;
    private final AmazonS3Handler amazonS3Handler;
    private final StorageDataMapper storageDataMapper;
    private final AppOrderRepository appOrderRepository;


    @Autowired
    public StorageDeleteHandler(StorageRepository storageRepository,
                                AmazonS3Handler amazonS3Handler, StorageDataMapper storageDataMapper,
                                AppOrderRepository appOrderRepository) {
        this.storageRepository = storageRepository;
        this.amazonS3Handler = amazonS3Handler;
        this.storageDataMapper = storageDataMapper;
        this.appOrderRepository = appOrderRepository;
    }


    @Transactional
    public void deleteStorageByMessage(StorageDeleteApprovalResponse storageDeleteApprovalResponse) {
        storageRepository.findByAppOrderId(new AppOrderId(storageDeleteApprovalResponse.getAppOrderId()))
                .ifPresent(storage -> {
                    amazonS3Handler.remove(storage.getFilename());
                    storageRepository.delete(storage);
                    AppOrder appOrder = storageDataMapper
                            .convertStorageDeleteApprovalResponseToAppOrder(storageDeleteApprovalResponse);
                    appOrderRepository.delete(appOrder);
                });
    }

    @Transactional
    public void deleteStorageByCommand(StorageDeleteCommand storageDeleteCommand) {

        Storage storage = storageRepository.findByAppOrderId(new AppOrderId(storageDeleteCommand.getAppOrderId()))
                .orElseThrow(() -> new StorageNotFoundException(String
                        .format("Storage is not found by appOrderId : %s", storageDeleteCommand.getAppOrderId())));

        amazonS3Handler.remove(storage.getFilename());
        storageRepository.delete(storage);
        AppOrder appOrder = storageDataMapper
                .convertStorageDeleteCommandToAppOrder(storageDeleteCommand);
        appOrderRepository.delete(appOrder);
    }
}
