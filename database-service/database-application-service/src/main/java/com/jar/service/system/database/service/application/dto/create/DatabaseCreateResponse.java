package com.jar.service.system.database.service.application.dto.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DatabaseCreateResponse {

    private final String accessUrl;
}
