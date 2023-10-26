package com.jar.service.system.database.service.kafka.mapper;

import com.jar.service.system.common.avro.model.DatabaseAvroModel;
import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DatabaseMessageMapper {


    public DatabaseDeleteCommand convertDatabaseAvroModelToDatabaseDeleteCommand(DatabaseAvroModel databaseAvroModel) {
        return DatabaseDeleteCommand.builder()
                .userId(UUID.fromString(databaseAvroModel.getUserId()))
                .build();
    }
}
