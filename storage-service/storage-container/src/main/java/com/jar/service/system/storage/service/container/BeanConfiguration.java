package com.jar.service.system.storage.service.container;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.jar.service.system.storage.service.domain.StorageDomainService;
import com.jar.service.system.storage.service.domain.StorageDomainServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class BeanConfiguration {

    @Bean
    public StorageDomainService storageDomainService() {
        return new StorageDomainServiceImpl();
    }


}
