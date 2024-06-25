package com.ssu.eatssu.global.auth.jwt

data class Tokens(
    val accessToken: String,
    val refreshToken: String
)
