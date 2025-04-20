package com.khcodings.tasky.shared.response

data class SuccessResponse<T>(
    val actionResponse: ActionResponse? = null,
    val data: T? = null,
)

