package com.jar.service.system.container.service.application.dto.update;

import com.jar.service.system.common.domain.valueobject.ContainerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ContainerUpdateResponse {

    private final String applicationName;
    private final ContainerStatus containerStatus;
}
