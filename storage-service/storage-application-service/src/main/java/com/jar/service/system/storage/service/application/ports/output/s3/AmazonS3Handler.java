package com.jar.service.system.storage.service.application.ports.output.s3;

import com.jar.service.system.storage.service.domain.valueobject.StorageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonS3Handler {
    StorageInfo uploadURL(MultipartFile multipartFile) throws IOException;

    void remove(String filename);
}
