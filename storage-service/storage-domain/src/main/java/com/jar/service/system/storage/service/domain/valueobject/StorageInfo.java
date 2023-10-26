package com.jar.service.system.storage.service.domain.valueobject;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StorageInfo {

    private final String filename;
    private final String fileUrl;
    private final String fileType;

    @Builder
    public StorageInfo(String filename, String fileUrl, String fileType) {
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "StorageInfo{" +
                "filename='" + filename + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
