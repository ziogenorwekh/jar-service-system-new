package com.jar.service.system.database.service;

import com.jar.service.system.common.domain.valueobject.UserId;
import com.jar.service.system.database.service.application.ports.output.repository.DatabaseRepository;
import com.jar.service.system.database.service.domain.entity.Database;
import com.jar.service.system.database.service.jpa.entity.DatabaseEntity;
import com.jar.service.system.database.service.application.exception.DatabaseSchemaException;
import com.jar.service.system.database.service.jpa.repository.DatabaseJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@DataJpaTest
@ActiveProfiles(profiles = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.NONE)
@ContextConfiguration(classes = DatabaseServiceJPAConfigurationTest.class)
@Transactional
public class DatabaseServiceJPATest {


    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private DatabaseJpaRepository databaseJpaRepository;

    private Database rDbInit;

    private Database duplicatedDb;

    private Database usingReservedWord;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    public void start() {

        rDbInit = Database.builder()
                .databaseName("tdb")
                .databaseUsername("tuser")
                .databasePassword("12345678")
                .userId(new UserId(userId))
                .build();
        rDbInit.initializeDatabase();

        duplicatedDb = Database.builder()
                .databaseName(rDbInit.getDatabaseName())
                .databaseUsername(rDbInit.getDatabaseUsername())
                .databasePassword(rDbInit.getDatabasePassword())
                .userId(new UserId(userId))
                .build();
        duplicatedDb.initializeDatabase();

        usingReservedWord = Database.builder()
                .databaseName("MOD")
                .databaseUsername(rDbInit.getDatabaseUsername())
                .databasePassword(rDbInit.getDatabasePassword())
                .userId(new UserId(userId))
                .build();
        usingReservedWord.initializeDatabase();

    }

    @AfterEach
    public void end() {
        databaseRepository.delete(rDbInit);
    }

    @Test
    @DisplayName("데이터베이스 사용자 저장하기")
    public void saveDatabaseAndDatabaseEntitySave() {
        Database save = databaseRepository.save(rDbInit);

        Optional<DatabaseEntity> databaseEntity = databaseJpaRepository
                .findDatabaseEntityByDatabaseId(save.getId().getValue());

        Assertions.assertEquals(databaseEntity.get().getDatabaseId(), rDbInit.getId().getValue());
    }

    @DisplayName("데이터베이스 예약어가 사용된 경우")
    @Test
    public void saveDatabaseUsingReservedWord() {

        DatabaseSchemaException exception = Assertions.assertThrows(DatabaseSchemaException.class, () -> {
            databaseRepository.save(usingReservedWord);
        });
        Assertions.assertEquals("DatabaseName or DatabaseUsername can't registry.",exception.getMessage());
    }

    @Test
    @DisplayName("데이터베이스에 사용자나 데이터베이스 이름이 겹치는 경우")
    public void ifExistDatabaseUserOrDatabaseNameItCausesError() {

        Assertions.assertNotEquals(rDbInit.getId(), duplicatedDb.getId());
        databaseRepository.save(rDbInit);
        Optional<Database> savedDatabase = databaseRepository.findByUserId(new UserId(userId));
        log.info(savedDatabase.toString());
        Exception exception = Assertions.assertThrows(DatabaseSchemaException.class, () -> {
            databaseRepository.save(duplicatedDb);
        });
        Assertions.assertEquals("Duplicated databaseName. OR using Reserved Word.",
                exception.getMessage());
    }
}
