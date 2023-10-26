package com.jar.service.system.gateway.service.application.filter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.jar.service.system.gateway.service.application.filter.dto.JwtTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class DecodeJwt {


    private final Environment env;

    @Autowired
    public DecodeJwt(Environment env) {
        this.env = env;
    }

    public JwtTokenDto decodeToken(String token) {
        String issuer;
        String tokenSecret = env.getProperty("server.token.secret");
        log.info("token secret is : {}", tokenSecret);
        try {
            issuer = JWT.require(Algorithm.HMAC256(Objects.requireNonNull(
                    tokenSecret))).build().verify(token).getIssuer();
        } catch (JWTDecodeException | AlgorithmMismatchException | SignatureVerificationException
                 | TokenExpiredException e) {
            return JwtTokenDto.builder().isSuccess(false).error(e.getMessage()).build();
        }
        return JwtTokenDto.builder().isSuccess(true).userId(issuer).build();
    }
}
