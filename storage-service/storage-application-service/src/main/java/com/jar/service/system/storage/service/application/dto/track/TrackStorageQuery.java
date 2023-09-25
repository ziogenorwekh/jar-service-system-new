package com.jar.service.system.storage.service.application.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackStorageQuery {

    private final UUID storageId;
}
