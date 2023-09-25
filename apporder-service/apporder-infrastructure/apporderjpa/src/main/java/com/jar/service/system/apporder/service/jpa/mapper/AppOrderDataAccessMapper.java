package com.jar.service.system.apporder.service.jpa.mapper;

import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.domain.valueobject.ServerConfig;
import com.jar.service.system.apporder.service.jpa.entity.AppOrderEntity;
import com.jar.service.system.apporder.service.jpa.entity.ContainerEntity;
import com.jar.service.system.apporder.service.jpa.entity.StorageEntity;
import com.jar.service.system.apporder.service.jpa.entity.UserEntity;
import com.jar.service.system.common.domain.valueobject.AppOrderId;
import com.jar.service.system.common.domain.valueobject.ContainerId;
import com.jar.service.system.common.domain.valueobject.StorageId;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppOrderDataAccessMapper {


    public AppOrderEntity convertAppOrderToAppOrderEntity(AppOrder appOrder) {
        return AppOrderEntity.builder()
                .appOrderId(appOrder.getId().getValue())
                .applicationName(appOrder.getServerConfig().getApplicationName())
                .serverPort(appOrder.getServerConfig().getServerPort())
                .endPoint(appOrder.getServerConfig().getEndPoint())
                .javaVersion(appOrder.getServerConfig().getJavaVersion())
                .error(Optional.ofNullable(appOrder.getErrorResult()).orElse(""))
                .applicationStatus(appOrder.getApplicationStatus())
                .userId(appOrder.getUserId() == null ? null : appOrder.getUserId().getValue())
                .storageId(appOrder.getStorageId() == null ? null : appOrder.getStorageId().getValue())
                .containerId(appOrder.getContainerId() == null ? null : appOrder.getContainerId().getValue())
                .build();
    }

    public AppOrder convertAppOrderEntityToAppOrder(AppOrderEntity appOrderEntity) {
        ServerConfig serverConfig = ServerConfig.builder()
                .applicationName(appOrderEntity.getApplicationName())
                .serverPort(appOrderEntity.getServerPort())
                .endPoint(appOrderEntity.getEndPoint())
                .javaVersion(appOrderEntity.getJavaVersion())
                .build();
        return AppOrder.builder()
                .appOrderId(new AppOrderId(appOrderEntity.getAppOrderId()))
                .serverConfig(serverConfig)
                .errorResult(appOrderEntity.getError())
                .userId(appOrderEntity.getUserId() == null ? null :
                        new UserId(appOrderEntity.getUserId()))
                .containerId(appOrderEntity.getContainerId() == null ? null :
                        new ContainerId(appOrderEntity.getContainerId()))
                .storageId(appOrderEntity.getStorageId() == null ? null :
                        new StorageId(appOrderEntity.getStorageId()))
                .applicationStatus(appOrderEntity.getApplicationStatus())
                .build();
    }


}
