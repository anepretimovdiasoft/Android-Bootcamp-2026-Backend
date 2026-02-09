package com.nikshet.industrialbackend.config

import com.nikshet.industrialbackend.providers.JwtTokensProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {

    @Bean
    fun tokensProvider(
        @Value($$"${jwt.secret}") secret: String,
        @Value($$"${jwt.access-token.expiration-ms:604800000}") accessTokenExpirationMs: Long,
        @Value($$"${jwt.refresh-token.expiration-ms:2592000000}") refreshTokenExpirationMs: Long
    ): JwtTokensProvider {
        return JwtTokensProvider(
            secret = secret,
            accessTokenExpirationMs = accessTokenExpirationMs,
            refreshTokenExpirationMs = refreshTokenExpirationMs
        )
    }
}