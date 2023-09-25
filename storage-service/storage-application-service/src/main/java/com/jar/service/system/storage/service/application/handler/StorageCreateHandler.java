package com.jar.service.system.storage.service.application.handler;

import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateCommand;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateResponse;
import com.jar.service.system.storage.service.application.dto.message.AppOrderCreatedResponse;
import com.jar.service.system.storage.service.application.exception.StorageAmazonS3Exception;
import com.jar.service.system.storage.service.application.exception.StorageAppOrderNotFoundException;
import com.jar.service.system.storage.service.application.exception.StorageApplicationException;
import com.jar.service.system.storage.service.application.mapper.StorageDataMapper;
import com.jar.service.system.storage.service.application.ports.output.publisher.StorageEventPublisher;
import com.jar.service.system.storage.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.storage.service.application.ports.output.repository.StorageRepository;
import com.jar.service.system.storage.service.application.ports.output.s3.AmazonS3Handler;
import com.jar.service.system.storage.service.domain.StorageDomainService;
import com.jar.service.system.storage.service.domain.entity.AppOrder;
import com.jar.service.system.storage.service.domain.entity.Storage;
import com.jar.service.system.storage.service.domain.event.StorageCreatedEvent;
import com.jar.service.system.storage.service.domain.event.StorageRejectedEvent;
import com.jar.service.system.storage.service.domain.valueobject.StorageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Component
public class StorageCreateHandler {

    private final StorageRepository storageRepository;
    private final AmazonS3Handler amazonS3Handler;
    private final StorageDomainService storageDomainService;
    private final StorageDataMapper storageDataMapper;
    private final StorageEventPublisher storageEventPublisher;
    private final AppOrderRepository appOrderRepository;


    @Autowired
    public StorageCreateHandler(StorageRepository storageRepository, AmazonS3Handler amazonS3Handler,
                                StorageDomainService storageDomainService,
                                StorageDataMapper storageDataMapper,
                                StorageEventPublisher storageEventPublisher,
                                AppOrderRepository appOrderRepository) {
        this.storageRepository = storageRepository;
        this.amazonS3Handler = amazonS3Handler;
        this.storageDomainService = storageDomainService;
        this.storageDataMapper = storageDataMapper;
        this.storageEventPublisher = storageEventPublisher;
        this.appOrderRepository = appOrderRepository;
    }

    @Transactional
    public void saveAppOrder(AppOrderCreatedResponse appOrderCreatedResponse) {
        AppOrder appOrder = storageDataMapper
                .convertAppOrderCreatedResponseToAppOrder(appOrderCreatedResponse);
        AppOrder saved = appOrderRepository.save(appOrder);
        log.info("saved AppOrder by Id ; {}",saved.getId().getValue());
    }

    @Transactional
    public StorageCreateResponse saveStorage(StorageCreateCommand storageCreateCommand) {
        checkExistAppOrderId(storageCreateCommand);
        Storage storage = initialize(storageCreateCommand);

        log.info("multipart file type is : {}", storageCreateCommand.getMultipartFile().getContentType());

        try {
            StorageInfo storageInfo = amazonS3Handler.uploadURL(storageCreateCommand.getMultipartFile());
            log.debug("Storage Information is : {}", storageInfo.toString());
            createEvent(storage, storageInfo);
            Storage saved = storageRepository.save(storage);
            return storageDataMapper.convertStorageToStorageCreateResponse(saved);
        } catch (StorageAmazonS3Exception e) {
            log.error("error Message is : {}", e.getMessage());
            errorEvent(storage, "S3 Storage upload failed.");
            throw new StorageApplicationException("S3 Storage upload failed.");
        } catch (IOException e) {
            log.error("error Message is : {}", e.getMessage());
            errorEvent(storage, "Internal file covert error.");
            throw new StorageApplicationException("Internal file covert error.");
        }
    }

    private void createEvent(Storage storage, StorageInfo storageInfo) {
        log.debug("Private method createEvent, Storage Information is : {}", storageInfo.toString());
        StorageCreatedEvent storageCreatedEvent = storageDomainService.saveStorage(storage, storageInfo);
        storageEventPublisher.publish(storageCreatedEvent);
    }

    private void errorEvent(Storage storage, String error) {
        StorageRejectedEvent storageRejectedEvent = storageDomainService
                .rejectedSaveStorage(storage, error);
        storageEventPublisher.publish(storageRejectedEvent);
    }

    private Storage initialize(StorageCreateCommand storageCreateCommand) {
        Storage storage = storageDataMapper
                .convertStorageCreateCommandToStorage(storageCreateCommand);
        storageDomainService.initializeStorage(storage);
        return storage;
    }

    private void checkExistAppOrderId(StorageCreateCommand storageCreateCommand) {
        appOrderRepository.findByAppOrderId(new AppOrderId(storageCreateCommand.getAppOrderId()))
                .orElseThrow(() -> new StorageAppOrderNotFoundException(
                        "AppOrder not found. If you have created an AppOrder, " +
                                "please wait until it is registered."));
    }
}
