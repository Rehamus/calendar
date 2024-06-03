package com.sparta.calendar.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {

    @Value("${jwt.secret.key}")
    private String jwtKey;

    @Value("${CALENDAR_DB_ADMIN}")
    private String ADMIN;
}
