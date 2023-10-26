package com.jar.service.system.apporder.service.application.mapper;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.UserDeleteApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackAppOrderCurtResponse;
import com.jar.service.system.apporder.service.application.dto.track.TrackUserQuery;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.Container;
import com.jar.service.system.apporder.service.domain.entity.Storage;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.domain.valueobject.ContainerConfig;
import com.jar.service.system.apporder.service.domain.valueobject.ServerConfig;
import com.jar.service.system.common.domain.valueobject.*;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AppOrderDataMapper {


    public AppOrder convertAppOrderCreateCommandToAppOrder(AppOrderCreateCommand appOrderCreateCommand) {
        return AppOrder.builder()
                .serverConfig(new ServerConfig("",
                        appOrderCreateCommand.getApplicationName(), appOrderCreateCommand.getServerPort(),
                        appOrderCreateCommand.getJavaVersion()))
                .userId(new UserId(appOrderCreateCommand.getUserId()))
                .build();
    }

    public AppOrderCreateResponse convertAppOrderToAppOrderCreateResponse(AppOrder appOrder) {
        return AppOrderCreateResponse.builder()
                .appOrderId(appOrder.getId().getValue())
                .applicationStatus(appOrder.getApplicationStatus())
                .build();
    }

    public TrackAppOrderResponse convertAppOrderToTrackAppOrderResponse(AppOrder appOrder) {
        return TrackAppOrderResponse.builder()
                .applicationName(Optional.ofNullable(appOrder.getServerConfig().getApplicationName())
                        .orElse(""))
                .endPoint(Optional.ofNullable(appOrder.getServerConfig().getEndPoint()).orElse(""))
                .serverPort(Optional.ofNullable(appOrder.getServerConfig().getServerPort()).orElse(0))
                .userId(Optional.ofNullable(appOrder.getUserId().getValue()).orElse(UUID.fromString("")))
                .containerId(Optional.ofNullable(appOrder.getContainerId().getValue()).orElse(UUID.fromString("")))
                .javaVersion(Optional.ofNullable(appOrder.getServerConfig().getJavaVersion()).orElse(0))
                .build();
    }

    public TrackAppOrderCurtResponse convertAppOrderToTrackAppOrderResponses(AppOrder appOrder) {
        return TrackAppOrderCurtResponse.builder()
                .applicationName(appOrder.getServerConfig().getApplicationName())
                .containerId(appOrder.getContainerId().getValue() == null ? UUID.fromString("") :
                        appOrder.getContainerId().getValue())
                .appOrderId(appOrder.getId().getValue())
                .build();
    }

    public Storage convertStorageApprovalResponseToStorage(StorageApprovalResponse storageApprovalResponse) {
        return Storage.builder()
                .storageId(new StorageId(storageApprovalResponse.getStorageId()))
                .storageStatus(storageApprovalResponse.getStorageStatus())
                .appOrderId(new AppOrderId(storageApprovalResponse.getAppOrderId()))
                .filename(storageApprovalResponse.getFilename())
                .fileType(storageApprovalResponse.getFileType())
                .fileUrl(storageApprovalResponse.getFileUrl())
                .error(Optional.ofNullable(storageApprovalResponse.getError()).orElse(""))
                .build();
    }

    public Container convertContainerApprovalResponseToContainer(ContainerApprovalResponse containerApprovalResponse) {
        return Container.builder()
                .javaVersion(containerApprovalResponse.getJavaVersion())
                .s3URL("this service is not necessary s3 URL.")
                .serverPort(containerApprovalResponse.getServerPort())
                .containerId(new ContainerId(containerApprovalResponse.getContainerId()))
                .appOrderId(new AppOrderId(containerApprovalResponse.getAppOrderId()))
                .containerStatus(containerApprovalResponse.getContainerStatus())
                .applicationName(containerApprovalResponse.getApplicationName())
                .dockerContainerId(new DockerContainerId(containerApprovalResponse.getDockerContainerId()))
                .error(Optional.ofNullable(containerApprovalResponse.getError()).orElse(""))
                .build();
    }

    public ContainerConfig convertServerConfigToContainerConfig(ServerConfig serverConfig,String s3Url) {
        return ContainerConfig.builder()
                .serverPort(serverConfig.getServerPort())
                .applicationName(serverConfig.getApplicationName())
                .s3URL(s3Url)
                .build();
    }

    public User convertUserDeleteApprovalResponseToUser(UserDeleteApprovalResponse userDeleteApprovalResponse) {
        return User.builder()
                .userId(new UserId(userDeleteApprovalResponse.getUserId()))
                .build();
    }

    public User convertAppOrderCreateCommandToUser(AppOrderCreateCommand appOrderCreateCommand) {
        return User.builder()
                .userId(new UserId(appOrderCreateCommand.getUserId())).build();
    }

    public User convertTrackUserQueryToUser(TrackUserQuery trackUserQuery) {
        return User.builder().userId(new UserId(trackUserQuery.getUserId())).build();
    }

    public ServerConfig convertContainerWithDomainToServerConfig(Container container, String endPoint) {
        return ServerConfig.builder()
                .javaVersion(container.getJavaVersion())
                .endPoint(endPoint)
                .serverPort(container.getServerPort())
                .applicationName(container.getApplicationName())
                .build();
    }
}
