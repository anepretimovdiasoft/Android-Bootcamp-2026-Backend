package ru.sicampus.bootcamp2026.config;

import org.springframework.core.io.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private Resource publicKey;
    private Resource privateKey;
    private long accessTokenExpirationMs;
    private long refreshTokenExpirationMs;
}
