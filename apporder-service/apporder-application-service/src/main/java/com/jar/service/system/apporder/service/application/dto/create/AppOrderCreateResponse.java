package com.jar.service.system.apporder.service.application.dto.create;

import com.jar.service.system.common.domain.valueobject.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AppOrderCreateResponse {

    private final UUID appOrderId;
    private final ApplicationStatus applicationStatus;

}
