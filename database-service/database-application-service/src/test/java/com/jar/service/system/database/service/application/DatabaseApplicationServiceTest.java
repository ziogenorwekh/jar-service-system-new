package com.jar.service.system.database.service.application;

import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateCommand;
import com.jar.service.system.database.service.application.dto.create.DatabaseCreateResponse;
import com.jar.service.system.database.service.application.dto.update.DatabasePwdUpdateCommand;
import com.jar.service.system.database.service.application.dto.update.DatabaseUpdatedResponse;
import com.jar.service.system.database.service.application.exception.DatabaseApplicationException;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.common.domain.event.DomainEvent;
import com.jar.service.system.common.domain.valueobject.UserId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestDatabaseConfiguration.class)
public class DatabaseApplicationServiceTest {


    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private DatabaseApplicationService databaseApplicationService;

    private final UUID userId = UUID.randomUUID();
    private DatabaseCreateCommand databaseCreateCommand;

    private final String accessUrl = "successDatabaseUrl";

    private DatabasePwdUpdateCommand databasePwdUpdateCommand;

    private Database saved;

    @BeforeEach
    public void testBeforeInit() {

        databaseCreateCommand = DatabaseCreateCommand.builder()
                .userId(userId)
                .databaseName("testDatabase")
                .databaseUsername("testUser")
                .databasePassword("testPassword")
                .build();

        databasePwdUpdateCommand = DatabasePwdUpdateCommand.builder()
                .userId(userId)
                .newPassword("newPassword")
                .build();


        saved = Database.builder()
                .userId(new UserId(databaseCreateCommand.getUserId()))
                .databaseName(databaseCreateCommand.getDatabaseName())
                .databaseUsername(databaseCreateCommand.getDatabaseUsername())
                .databasePassword(databaseCreateCommand.getDatabasePassword())
                .accessUrl(accessUrl)
                .build();
    }

    @Test
    @DisplayName("데이터베이스를 저장하기 및 중복된 데이터베이스명이나 유저이름은 사용할 수 없기")
    public void createDBTest() {
        when(databaseRepository.findByDatabaseNameOrDatabaseUsername(databaseCreateCommand.getDatabaseUsername()
                , databaseCreateCommand.getDatabaseName())).thenReturn(Optional.empty());
        when(databaseRepository.save(any(Database.class))).thenReturn(saved);

        DatabaseCreateResponse createResponse = databaseApplicationService
                .createDatabase(databaseCreateCommand);

        Assertions.assertThat(createResponse.getAccessUrl())
                .isEqualTo("successDatabaseUrl");

        when(databaseRepository.findByDatabaseNameOrDatabaseUsername(databaseCreateCommand.getDatabaseUsername()
                , databaseCreateCommand.getDatabaseName())).thenReturn(Optional.of(saved));

        DatabaseApplicationException databaseApplicationException =
                org.junit.jupiter.api.Assertions.assertThrows(DatabaseApplicationException.class, () -> {
                    databaseApplicationService.createDatabase(databaseCreateCommand);
                });
        Assertions.assertThat(databaseApplicationException.getMessage())
                .isEqualTo("database name or username is already exist.");
    }

    @Test
    @DisplayName("사용자의 개인 데이터베이스 비밀번호 업데이트")
    public void updateDBPwdTest() {
        when(databaseRepository.findByUserId(new UserId(userId)))
                .thenReturn(Optional.of(saved));

        DatabaseUpdatedResponse databaseUpdatedResponse = databaseApplicationService
                .updateDatabaseNewPassword(databasePwdUpdateCommand);

        org.junit.jupiter.api.Assertions
                .assertEquals("newPassword",databaseUpdatedResponse.getNewPassword());
    }
}
