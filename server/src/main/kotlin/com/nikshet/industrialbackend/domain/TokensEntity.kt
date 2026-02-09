package com.nikshet.industrialbackend.domain

data class TokensEntity(
    val accessToken: String,
    val accessTokenExpiresIn: Long,
    val refreshToken: String,
    val refreshTokenExpiresIn: Long,
)