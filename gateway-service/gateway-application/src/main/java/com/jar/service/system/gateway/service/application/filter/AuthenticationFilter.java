package com.jar.service.system.gateway.service.application.filter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.jar.service.system.gateway.service.application.filter.config.Configuration;
import com.jar.service.system.gateway.service.application.filter.dto.JwtTokenDto;
import com.jar.service.system.gateway.service.application.filter.jwt.DecodeJwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
//@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<Configuration> {

    private final DecodeJwt decodeJwt;

    public AuthenticationFilter(DecodeJwt decodeJwt) {
        super(Configuration.class);
        this.decodeJwt = decodeJwt;
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {

            ServerHttpRequest serverHttpRequest = exchange.getRequest();

            if (!serverHttpRequest.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "AUTHORIZATION is null.", HttpStatus.UNAUTHORIZED);
            }

            List<String> authenticationKey = serverHttpRequest.getHeaders().get(HttpHeaders.AUTHORIZATION);
            String rawToken = authenticationKey.get(0);
            String token = rawToken.substring(0, "Bearer ".length());
            JwtTokenDto jwtTokenDto = decodeJwt.decodeToken(token, config.getTokenSecret());

            if (!jwtTokenDto.getIsSuccess()) {
                return onError(exchange, jwtTokenDto.getError(), HttpStatus.BAD_REQUEST);
            }

            exchange.getRequest().getHeaders().add("userId", jwtTokenDto.getUserId());
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(errMessage);
        response.getHeaders().add("error", errMessage);
        return response.setComplete();
    }
}
