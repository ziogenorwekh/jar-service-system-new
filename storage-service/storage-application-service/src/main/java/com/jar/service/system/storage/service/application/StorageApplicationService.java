package com.jar.service.system.storage.service.application;

import com.jar.service.system.storage.service.application.dto.create.StorageCreateCommand;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateResponse;
import com.jar.service.system.storage.service.application.dto.delete.StorageDeleteCommand;
import com.jar.service.system.storage.service.application.dto.track.TrackStorageQuery;
import com.jar.service.system.storage.service.application.dto.track.TrackStorageResponse;
import org.springframework.validation.annotation.Validated;

public interface StorageApplicationService {

    StorageCreateResponse saveStorage(@Validated StorageCreateCommand storageCreateCommand);

    TrackStorageResponse trackQueryStorage(@Validated TrackStorageQuery trackStorageQuery);

    void deleteStorageObject(@Validated StorageDeleteCommand storageDeleteCommand);

}
