package com.khcodings.logservice.domain.logger

import java.time.Instant

data class LogRequest(
    val source: String,
    val message: String,
    val timestamp: Instant = Instant.now()
)
