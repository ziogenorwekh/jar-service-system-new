package com.jar.service.system.database.service.kafka.adapter.listener;

import com.jar.service.system.common.avro.model.DatabaseAvroModel;
import com.jar.service.system.database.service.application.dto.delete.DatabaseDeleteCommand;
import com.jar.service.system.database.service.application.ports.input.listener.DatabaseUserResponseListener;
import com.jar.service.system.database.service.kafka.mapper.DatabaseMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.AvroRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DatabaseUserMessageListener implements com.jar.service.system.common.kafka.listener.KafkaListener<DatabaseAvroModel> {

    private final DatabaseUserResponseListener databaseUserResponseListener;
    private final DatabaseMessageMapper databaseMessageMapper;

    @Autowired
    public DatabaseUserMessageListener(DatabaseUserResponseListener databaseUserResponseListener,
                                       DatabaseMessageMapper databaseMessageMapper) {
        this.databaseUserResponseListener = databaseUserResponseListener;
        this.databaseMessageMapper = databaseMessageMapper;
    }

    @Override
    @KafkaListener(topics = "user-service-database-delete-topic")
    public void receive(@Payload List<DatabaseAvroModel> messaging,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> key) {
        try {
            messaging.forEach(databaseAvroModel -> {
                log.info("receive kafka message Avro model's user id : {}", databaseAvroModel.getUserId());
                DatabaseDeleteCommand databaseDeleteCommand = databaseMessageMapper
                        .convertDatabaseAvroModelToDatabaseDeleteCommand(databaseAvroModel);
                databaseUserResponseListener.deleteDatabase(databaseDeleteCommand);
            });

        } catch (AvroRuntimeException e) {
            log.error("error in database message listener : {}", e.getMessage());
        }

    }

}
