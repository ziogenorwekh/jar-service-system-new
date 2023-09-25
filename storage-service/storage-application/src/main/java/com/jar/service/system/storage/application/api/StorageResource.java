package com.jar.service.system.storage.application.api;

import com.jar.service.system.storage.service.application.StorageApplicationService;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateCommand;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateResponse;
import com.jar.service.system.storage.service.application.dto.delete.StorageDeleteCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class StorageResource {


    private final StorageApplicationService storageApplicationService;


    @Autowired
    public StorageResource(StorageApplicationService storageApplicationService) {
        this.storageApplicationService = storageApplicationService;
    }

    @RequestMapping(value = "/{appOrderId}/storages",method = RequestMethod.POST)
    public ResponseEntity<StorageCreateResponse> saveStorage(@PathVariable UUID appOrderId,
                                         @RequestPart(value = "file") MultipartFile file) {
        StorageCreateCommand storageCreateCommand = StorageCreateCommand.builder()
                .appOrderId(appOrderId)
                .multipartFile(file)
                .build();

        StorageCreateResponse storageCreateResponse = storageApplicationService
                .saveStorage(storageCreateCommand);

        return ResponseEntity.ok().body(storageCreateResponse);
    }

    @RequestMapping(value = "/{appOrderId}/storages",method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteStorage(@PathVariable UUID appOrderId) {
        storageApplicationService.
                deleteStorageObject(new StorageDeleteCommand(appOrderId));
        return ResponseEntity.noContent().build();
    }
}
