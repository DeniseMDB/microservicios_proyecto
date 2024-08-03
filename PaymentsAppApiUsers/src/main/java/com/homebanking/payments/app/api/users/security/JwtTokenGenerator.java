package com.homebanking.payments.app.api.users.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenGenerator {
    private final Environment environment;

    public JwtTokenGenerator(Environment environment) {
        this.environment = environment;
    }

    public String generateToken(String userId) {
        Instant now = Instant.now();
        long expirationTimeMillis = Long.parseLong(environment.getProperty("token.expiration_time"));

        String tokenSecret = RandomStringUtils.randomAlphanumeric(64);
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        return Jwts.builder()
                .subject(userId)
                .expiration(Date.from(now.plusMillis(expirationTimeMillis)))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();
    }
}
