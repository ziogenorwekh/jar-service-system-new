package com.jar.service.system.storage.service.s3.adapter;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jar.service.system.storage.service.application.exception.StorageAmazonS3Exception;
import com.jar.service.system.storage.service.application.exception.StorageApplicationException;
import com.jar.service.system.storage.service.application.ports.output.s3.AmazonS3Handler;
import com.jar.service.system.storage.service.domain.valueobject.StorageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class AmazonS3HandlerImpl implements AmazonS3Handler {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public AmazonS3HandlerImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public StorageInfo uploadURL(MultipartFile multipartFile) throws IOException {
        validateFileType(multipartFile);

        File file = convertMultipartFileToFile(multipartFile);

        upload(file);

        StorageInfo storageInfo = StorageInfo.builder()
                .fileUrl(amazonS3.getUrl(bucket, file.getName()).toString())
                .filename(file.getName())
                .fileType(getFileType(multipartFile))
                .build();

        removeLocalFile(file);

        log.info("storage info data is : {}", storageInfo.toString());
        log.info("S3 upload successful processing.");
        return storageInfo;
    }

    @Override
    public void remove(String filename) {
        try {
            amazonS3.deleteObject(bucket, filename);
            log.trace("storage remove successful filename is : {}", filename);
        } catch (Exception e) {
            throw new StorageAmazonS3Exception(e.getMessage());
        }
    }

    private void upload(File file) {
        try {
            amazonS3.putObject(new PutObjectRequest(bucket, file.getName(), file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (Exception e) {
            throw new StorageAmazonS3Exception(e.getMessage());
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        String filename = String.format("%s-%s", UUID.randomUUID(), multipartFile.getOriginalFilename());
        File convertedFile = new File(filename);

        if (convertedFile.createNewFile()) {
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        }
        return convertedFile;
    }

    private String getFileType(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileType = "";

        if (originalFilename != null && originalFilename.lastIndexOf('.') != -1) {
            fileType = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        }
        return fileType;
    }

    private void validateFileType(MultipartFile multipartFile) {
        if (!Objects.requireNonNull(multipartFile.getOriginalFilename())
                .toLowerCase().endsWith(".jar")) {
            throw new StorageApplicationException("Invalid file type. Only .jar files are allowed.");
        }
    }

    private void removeLocalFile(File targetFile) {
        targetFile.delete();
    }

}
