package com.jar.service.system.storage.service.application.exception;

public class StorageAmazonS3Exception extends RuntimeException {
    public StorageAmazonS3Exception(String message) {
        super(message);
    }

    public StorageAmazonS3Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
