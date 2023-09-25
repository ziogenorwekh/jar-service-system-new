package com.jar.service.system.user.service.kafka.adapter.listener;

import com.jar.service.system.apporder.service.application.dto.message.UserDeleteApprovalResponse;
import com.jar.service.system.apporder.service.application.ports.input.listener.AppOrderUserResponseListener;
import com.jar.service.system.common.avro.model.UserAvroModel;
import com.jar.service.system.common.kafka.listener.KafkaListener;
import com.jar.service.system.user.service.kafka.mapper.AppOrderMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jar.service.system.common.avro.model.MessageType.DELETE;

@Slf4j
@Component
public class AppOrderUserMessageListener implements KafkaListener<UserAvroModel> {

    private final AppOrderUserResponseListener appOrderUserResponseListener;
    private final AppOrderMessageMapper appOrderMessageMapper;

    @Autowired
    public AppOrderUserMessageListener(AppOrderUserResponseListener appOrderUserResponseListener,
                                       AppOrderMessageMapper appOrderMessageMapper) {
        this.appOrderUserResponseListener = appOrderUserResponseListener;
        this.appOrderMessageMapper = appOrderMessageMapper;
    }


    @Override
    @org.springframework.kafka.annotation.KafkaListener(groupId = "user-listener-group",
            topics = "user-service-apporder-delete-topic")
    public void receive(List<UserAvroModel> userAvroModels, List<String> key) {

        userAvroModels.forEach(userAvroModel -> {
            UserDeleteApprovalResponse userDeleteApprovalResponse = appOrderMessageMapper
                    .convertUserAvroModelToUserDeleteApprovalResponse(userAvroModel);
            switch (userAvroModel.getMessageType()) {
                case DELETE -> {
                    log.info("DELETE ALL APPORDER by userId : {}",userAvroModel.getUserId());
                    appOrderUserResponseListener.deleteAll(userDeleteApprovalResponse);
                }
                case CREATE,FAIL,NO_DEF,UPDATE,REJECT -> {
                    log.warn("Not support Message type except for DELETE");
                }
            }
        });
    }
}
