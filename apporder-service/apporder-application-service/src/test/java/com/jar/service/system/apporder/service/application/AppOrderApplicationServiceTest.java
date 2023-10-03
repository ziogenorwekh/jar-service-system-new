package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.ContainerApprovalResponse;
import com.jar.service.system.apporder.service.application.dto.message.StorageApprovalResponse;
import com.jar.service.system.apporder.service.application.ports.output.repository.UserRepository;
import com.jar.service.system.apporder.service.domain.event.AppOrderEvent;
import com.jar.service.system.apporder.service.domain.valueobject.ServerConfig;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateCommand;
import com.jar.service.system.apporder.service.application.dto.create.AppOrderCreateResponse;
import com.jar.service.system.apporder.service.domain.entity.AppOrder;
import com.jar.service.system.apporder.service.domain.entity.User;
import com.jar.service.system.apporder.service.application.ports.output.repository.AppOrderRepository;
import com.jar.service.system.common.domain.entitiy.AggregateRoot;
import com.jar.service.system.common.domain.valueobject.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestAppOrderConfiguration.class)
public class AppOrderApplicationServiceTest {


    @Autowired
    private AppOrderRepository appOrderRepository;
    @Autowired
    private AppOrderApplicationService appOrderApplicationService;

    @Autowired
    private AppOrderContainerMessageHelper appOrderContainerMessageHelper;
    @Autowired
    private AppOrderStorageMessageHelper appOrderStorageMessageHelper;
    @Autowired
    private UserRepository userRepository;

    private AppOrderCreateCommand appOrderCreateCommand;
    private User user;
    private final UUID userId = UUID.randomUUID();
    private final UUID appOrderId = UUID.randomUUID();
    private final UUID storageId = UUID.randomUUID();
    private AppOrder appOrder;
    private StorageApprovalResponse successStorageApproval;
    private ServerConfig serverConfig;
    private String serverUrl = "";
    private final String applicationName = "testApplication";
    private final Integer serverPort = 8080;
    private final Integer javaVersion = 17;

    @BeforeEach
    public void init() {
        appOrderCreateCommand = new AppOrderCreateCommand(applicationName, serverPort, javaVersion);
        appOrderCreateCommand.setUserId(userId);

        serverConfig = new ServerConfig(serverUrl, applicationName, serverPort, javaVersion);
        user = User.builder().userId(new UserId(userId)).build();
    }

    @DisplayName("애플리케이션 생성")
    @Test
    public void createAppOrder() {
        appOrder = AppOrder.builder()
                .appOrderId(new AppOrderId(appOrderId))
                .userId(new UserId(userId))
                .serverConfig(serverConfig)
                .applicationStatus(ApplicationStatus.PENDING)
                .errorResult("")
                .build();

        when(appOrderRepository.save(any(AppOrder.class)))
                .thenReturn(appOrder);

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        AppOrderCreateResponse orderCreateResponse =
                appOrderApplicationService.createAppOrder(appOrderCreateCommand);

        Assertions.assertNotNull(appOrder.getId());
        Assertions.assertEquals(appOrderId, orderCreateResponse.getAppOrderId());
        Assertions.assertEquals(ApplicationStatus.PENDING, orderCreateResponse.getApplicationStatus());
    }


    @DisplayName("스토리지 서비스에서 파일이 저장되면 컨테이너 만들기")
    @Test
    public void ifStorageSavedItWouldBeNextStepToContainerInitialize() {
        appOrder = AppOrder.builder()
                .appOrderId(new AppOrderId(appOrderId))
                .userId(new UserId(userId))
                .serverConfig(serverConfig)
                .storageId(null)
                .applicationStatus(ApplicationStatus.PENDING)
                .errorResult("")
                .build();

        when(appOrderRepository.findByAppOrderId(appOrder.getId()))
                .thenReturn(Optional.of(appOrder));

        successStorageApproval = StorageApprovalResponse.builder().
                storageId(storageId)
                .fileUrl("testURL")
                .fileType("testType")
                .storageStatus(StorageStatus.SAVED)
                .filename("test-file")
                .error("")
                .appOrderId(appOrder.getId().getValue())
                .build();

//        AppOrderEvent<? extends AggregateRoot<? extends BaseId<UUID>>> appOrderEvent =
//                appOrderStorageMessageHelper.storageProcessing(successStorageApproval);
//        Assertions.assertEquals(MessageType.CREATE, appOrderEvent.getMessageType());
    }


    @DisplayName("컨테이너가 정상적으로 생성 되는 경우")
    @Test
    public void containerSuccessfulProcessing() {
        UUID containerId = UUID.randomUUID();

        appOrder = AppOrder.builder()
                .appOrderId(new AppOrderId(appOrderId))
                .userId(new UserId(userId))
                .serverConfig(serverConfig)
                .storageId(null)
                .applicationStatus(ApplicationStatus.CONTAINERIZING)
                .errorResult("")
                .build();

        when(appOrderRepository.findByAppOrderId(appOrder.getId()))
                .thenReturn(Optional.ofNullable(appOrder));

        ContainerApprovalResponse containerApprovalResponse = ContainerApprovalResponse.builder()
                .dockerContainerId("dockerId")
                .javaVersion(javaVersion)
                .serverPort(serverPort)
                .applicationName(applicationName)
                .containerStatus(ContainerStatus.STARTED)
                .error("")
                .containerId(containerId)
                .appOrderId(appOrder.getId().getValue())
                .build();

//        AppOrderEvent<AppOrder> appOrderAppOrderEvent = appOrderContainerMessageHelper
//                .containerProcessing(containerApprovalResponse);
//        Assertions.assertEquals(MessageType.CREATE,appOrderAppOrderEvent.getMessageType());
    }
}
