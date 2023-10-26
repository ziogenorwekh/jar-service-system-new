package com.jar.service.system.gateway.service.application.filter;

import com.jar.service.system.gateway.service.application.filter.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CorsFilter extends AbstractGatewayFilterFactory<Configuration> {

    public CorsFilter() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (request.getMethod().matches(String.valueOf(HttpMethod.OPTIONS))) {
                response.getHeaders().set("Access-Control-Allow-Origin", config.getAccessControlAllowOrigin());
                response.getHeaders().set("Access-Control-Allow-Methods", config.getAccessControlAllowMethods());
                response.getHeaders().set("Access-Control-Allow-Headers", config.getAccessControlAllowHeaders());
                response.getHeaders().set("Access-Control-Allow-Credentials", config.getAccessControlAllowCredentials());
            }
            HttpMethod method = request.getMethod();
            log.info("where is come in ? : {}", request.getPath());
            log.info("come in method is : {}", method);
            return chain.filter(exchange);
        };
    }
}
