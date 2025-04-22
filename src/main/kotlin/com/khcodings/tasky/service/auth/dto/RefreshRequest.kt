package com.khcodings.tasky.service.auth.dto

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "Value cannot be Empty")
    val refreshToken: String
)
