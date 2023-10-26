package com.jar.service.system.apporder.service.application.dto.message;

import com.jar.service.system.common.domain.valueobject.StorageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class StorageApprovalResponse {
    private final UUID storageId;
    private final UUID appOrderId;
    private final String filename;
    private final String fileUrl;
    private final String fileType;
    private final StorageStatus storageStatus;
    private final String error;

    @Override
    public String toString() {
        return "StorageApprovalResponse{" +
                "storageId=" + storageId +
                ", appOrderId=" + appOrderId +
                ", filename='" + filename + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileType='" + fileType + '\'' +
                ", storageStatus=" + storageStatus +
                ", error='" + error + '\'' +
                '}';
    }
}
