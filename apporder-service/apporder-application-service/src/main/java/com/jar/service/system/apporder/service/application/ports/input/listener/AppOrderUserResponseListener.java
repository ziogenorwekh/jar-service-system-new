package com.jar.service.system.apporder.service.application.ports.input.listener;

import com.jar.service.system.apporder.service.application.dto.message.UserDeleteApprovalResponse;

public interface AppOrderUserResponseListener {

    void deleteAll(UserDeleteApprovalResponse userDeleteApprovalResponse);
}
