package com.jar.service.system.gateway.service.application.filter.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.jar.service.system.gateway.service.application.filter.dto.JwtTokenDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DecodeJwt {


    public JwtTokenDto decodeToken(String token, String tokenSecret) {
        String issuer;
        try {
            issuer = JWT.require(Algorithm.HMAC256(tokenSecret)).build().verify(token).getIssuer();
        } catch (JWTDecodeException | AlgorithmMismatchException | SignatureVerificationException
                 | TokenExpiredException e) {
            return JwtTokenDto.builder().isSuccess(false).error(e.getMessage()).build();
        }
        return JwtTokenDto.builder().isSuccess(true).userId(issuer).build();
    }
}
