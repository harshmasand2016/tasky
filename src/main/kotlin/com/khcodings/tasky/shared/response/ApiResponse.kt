package com.khcodings.tasky.shared.response

data class ApiResponse<T>(
    val data: T? = null,
    val errors: ErrorResponse? = null
)
