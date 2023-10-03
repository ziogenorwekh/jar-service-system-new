package com.jar.service.system.container.service.application.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrackContainerLogsResponse {

    private final String logs;
}
