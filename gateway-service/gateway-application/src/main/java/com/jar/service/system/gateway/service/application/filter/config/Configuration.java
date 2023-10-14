package com.jar.service.system.gateway.service.application.filter.config;

import lombok.Data;

@Data
public class Configuration {
    private String accessControlAllowOrigin;
    private String accessControlAllowMethods;
    private String accessControlAllowHeaders;
    private String accessControlAllowCredentials;

}
