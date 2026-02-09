package com.nikshet.industrialbackend.providers

import com.nikshet.industrialbackend.domain.TokensEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey
import kotlin.collections.mapKeys
import kotlin.text.toByteArray

class JwtTokensProvider(
    secret: String,
    private val accessTokenExpirationMs: Long,
    private val refreshTokenExpirationMs: Long,
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        secret.toByteArray(StandardCharsets.UTF_8)
    )

    init {
        require(secret.length >= 32) { "Secret must be at least 32 characters for HS256 security" }
        require(accessTokenExpirationMs > 0) { "Access token expiration must be positive" }
        require(refreshTokenExpirationMs > accessTokenExpirationMs) {
            "Refresh token expiration must be longer than access token expiration"
        }
    }

    fun generateTokensPairs(payload: Map<Any, Any>): TokensEntity {
        val safePayload = payload.mapKeys { it.key.toString() }
        val now = Date()

        val accessToken = Jwts.builder()
            .claims(safePayload)
            .issuedAt(now)
            .expiration(Date(now.time + accessTokenExpirationMs))
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .claims(safePayload)
            .issuedAt(now)
            .expiration(Date(now.time + refreshTokenExpirationMs))
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()

        return TokensEntity(
            accessToken = accessToken,
            accessTokenExpiresIn = accessTokenExpirationMs / 1000,
            refreshToken = refreshToken,
            refreshTokenExpiresIn = refreshTokenExpirationMs / 1000,
        )
    }

    fun getUserIdFromToken(token: String): UUID? {
        return try {
            val claims: Claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
            UUID.fromString(claims.subject)
        } catch (e: Exception) {
            null
        }
    }
}
