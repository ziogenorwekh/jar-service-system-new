package com.jar.service.system.storage.service.application;

import com.jar.service.system.storage.service.application.dto.create.StorageCreateCommand;
import com.jar.service.system.storage.service.application.dto.create.StorageCreateResponse;
import com.jar.service.system.storage.service.application.dto.delete.StorageDeleteCommand;
import com.jar.service.system.storage.service.application.dto.track.TrackStorageQuery;
import com.jar.service.system.storage.service.application.dto.track.TrackStorageResponse;
import com.jar.service.system.storage.service.application.handler.StorageCreateHandler;
import com.jar.service.system.storage.service.application.handler.StorageDeleteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageApplicationServiceImpl implements StorageApplicationService {

    private final StorageCreateHandler storageCreateHandler;

    private final StorageDeleteHandler storageDeleteHandler;

    @Autowired
    public StorageApplicationServiceImpl(StorageCreateHandler storageCreateHandler,
                                         StorageDeleteHandler storageDeleteHandler) {
        this.storageCreateHandler = storageCreateHandler;
        this.storageDeleteHandler = storageDeleteHandler;
    }

    @Override
    public StorageCreateResponse saveStorage(StorageCreateCommand storageCreateCommand) {
        return storageCreateHandler.saveStorage(storageCreateCommand);
    }

    @Override
    public TrackStorageResponse trackQueryStorage(TrackStorageQuery trackStorageQuery) {
        return null;
    }

    @Override
    public void deleteStorageObject(StorageDeleteCommand storageDeleteCommand) {
        storageDeleteHandler.deleteStorageByCommand(storageDeleteCommand);
    }
}
