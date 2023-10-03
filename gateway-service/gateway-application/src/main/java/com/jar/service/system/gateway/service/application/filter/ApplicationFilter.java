package com.jar.service.system.gateway.service.application.filter;

import com.jar.service.system.gateway.service.application.filter.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ApplicationFilter extends AbstractGatewayFilterFactory<Configuration> {
    public ApplicationFilter() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpMethod method = request.getMethod();
            log.info("where is come in ? : {}", request.getPath());
            log.info("come in method is : {}", method);
            return chain.filter(exchange);
        };
    }
}
