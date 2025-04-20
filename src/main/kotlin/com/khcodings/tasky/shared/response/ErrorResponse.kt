package com.khcodings.tasky.shared.response

data class ErrorResponse(
    val error_code: Int,
    val error_description: String
)