package com.khcodings.tasky.application.auth.dto

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
)
