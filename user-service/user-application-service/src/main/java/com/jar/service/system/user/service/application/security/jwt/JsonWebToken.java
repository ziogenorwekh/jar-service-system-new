package com.jar.service.system.user.service.application.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jar.service.system.user.service.application.dto.create.UserTokenResponse;
import com.jar.service.system.user.service.application.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
@Component
public class JsonWebToken {

    private Environment env;

    @Autowired
    public JsonWebToken(Environment env) {
        this.env = env;
    }


    public String generateToken(CustomUserDetails customUserDetails) {

        String tokenExpirationTime = env.getProperty("server.token.expiration");
        String secret = env.getProperty("server.token.secret");
        log.trace("expiration Time -> {} minutes", tokenExpirationTime);
        log.trace("token secret is -> {}", secret);
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        ZonedDateTime expirationTime = now.
                plusMinutes(Long.parseLong(Objects
                        .requireNonNull(tokenExpirationTime)));

        log.info("{} user is generating jwt", customUserDetails.getUsername());

        return JWT.create().withIssuer(customUserDetails.getUserId().toString())
                .withExpiresAt(expirationTime.toInstant())
                .sign(Algorithm.HMAC256(secret));
    }
}
