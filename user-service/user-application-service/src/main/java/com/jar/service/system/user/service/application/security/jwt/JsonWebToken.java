package com.jar.service.system.user.service.application.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jar.service.system.user.service.application.dto.create.UserTokenResponse;
import com.jar.service.system.user.service.application.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Component
public class JsonWebToken {

    @Value("${server.token.secret}")
    private String secret;

    public String generateToken(CustomUserDetails customUserDetails) {

        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        ZonedDateTime expirationTime = now.plusMinutes(15);

        log.info("{} user is generating jwt", customUserDetails.getUsername());

        return JWT.create().withIssuer(customUserDetails.getUserId().toString())
                .withExpiresAt(expirationTime.toInstant())
                .sign(Algorithm.HMAC256(secret));
    }
}
