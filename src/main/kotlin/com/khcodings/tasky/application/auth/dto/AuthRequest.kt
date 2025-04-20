package com.khcodings.tasky.application.auth.dto

import com.khcodings.tasky.shared.interfaces.ValidationGroups
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class AuthRequest(
    @field:Email(
        message = "Email format is invalid",
        groups = [ValidationGroups.FormatGroup::class]
    )
    @field:NotBlank(message = "Email cannot be Empty", groups = [ValidationGroups.NotBlankGroup::class])
    val email: String,
    @field:NotBlank(message = "Password cannot be Empty",groups = [ValidationGroups.NotBlankGroup::class])
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9\\s])[A-Za-z\\d[^\\s]]{6,10}$",
        message = "Password must be 6-10 characters long, include at least 1 uppercase letter, 1 lowercase letter, and 1 special character",
        groups = [ValidationGroups.FormatGroup::class]
    )
    val password: String
)
