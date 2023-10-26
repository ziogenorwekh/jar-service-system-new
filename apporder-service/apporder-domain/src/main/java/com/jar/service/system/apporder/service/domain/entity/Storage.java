package com.jar.service.system.apporder.service.domain.entity;

import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.StorageId;
import com.jar.service.system.common.domain.valueobject.StorageStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Storage extends AggregateRoot<StorageId> {

    private final String filename;
    private final String fileUrl;
    private final String fileType;
    private final StorageStatus storageStatus;
    private final String error;
    private final AppOrderId appOrderId;

    @Builder
    private Storage(StorageId storageId, String filename,
                    String fileUrl, String fileType,
                    StorageStatus storageStatus, String error, AppOrderId appOrderId) {
        super.setId(storageId);
        this.storageStatus = storageStatus;
        this.error = error;
        this.appOrderId = appOrderId;
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }
}
