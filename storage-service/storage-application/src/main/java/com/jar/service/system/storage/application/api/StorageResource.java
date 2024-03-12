package com.jar.service.system.storage.application.api;

import com.jar.service.system.storage.service.application.StorageApplicationService;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateCommand;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateResponse;
import com.jar.service.system.storage.service.application.dto.delete.StorageDeleteCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class StorageResource {


    private final StorageApplicationService storageApplicationService;


    @Autowired
    public StorageResource(StorageApplicationService storageApplicationService) {
        this.storageApplicationService = storageApplicationService;
    }

    /**
     * Upload User's Application File
     * @param appOrderId
     * @param userId
     * @param file
     * @return
     */
    @RequestMapping(value = "/storages/{appOrderId}",method = RequestMethod.POST)
    public ResponseEntity<StorageCreateResponse> saveStorage(@PathVariable UUID appOrderId,
                                         @RequestHeader("userId") UUID userId,
                                         @RequestPart(value = "file") MultipartFile file) {

        log.info("[CREATE] create {}'s storage by user -> {}", appOrderId, userId);
        StorageCreateCommand storageCreateCommand = StorageCreateCommand.builder()
                .appOrderId(appOrderId)
                .userId(userId)
                .multipartFile(file)
                .build();

        StorageCreateResponse storageCreateResponse = storageApplicationService
                .saveStorage(storageCreateCommand);

        return ResponseEntity.ok().body(storageCreateResponse);
    }


    /**
     * Delete User's Application File
     * @param appOrderId
     * @return
     */
    @RequestMapping(value = "/storages/{appOrderId}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteStorage(@PathVariable UUID appOrderId) {

        log.info("[DELETE] delete {}'s storage", appOrderId);
        storageApplicationService.
                deleteStorageObject(new StorageDeleteCommand(appOrderId));
        return ResponseEntity.noContent().build();
    }
}
