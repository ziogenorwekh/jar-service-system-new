package com.jar.service.system.apporder.service.application;

import com.jar.service.system.apporder.service.application.dto.message.UserDeleteApprovalResponse;
import com.jar.service.system.apporder.service.application.handler.AppOrderDeleteHandler;
import com.jar.service.system.apporder.service.application.ports.input.listener.AppOrderUserResponseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppOrderUserResponseListenerImpl implements AppOrderUserResponseListener {

    private final AppOrderDeleteHandler appOrderDeleteHandler;

    @Autowired
    public AppOrderUserResponseListenerImpl(AppOrderDeleteHandler appOrderDeleteHandler) {
        this.appOrderDeleteHandler = appOrderDeleteHandler;
    }

    @Override
    public void deleteAll(UserDeleteApprovalResponse userDeleteApprovalResponse) {
        appOrderDeleteHandler.deleteAllAppOrder(userDeleteApprovalResponse);
    }
}
