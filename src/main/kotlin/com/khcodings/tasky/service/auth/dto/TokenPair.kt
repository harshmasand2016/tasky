package com.khcodings.tasky.service.auth.dto

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
)
