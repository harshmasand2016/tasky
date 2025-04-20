package com.khcodings.tasky.application.tasks.dto

import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class TaskRequest(
    val id: String?,
    @field:NotBlank(message = "Task Name Can't be empty")
    val taskName: String,
    val taskContent: String,
    val taskColor: Long,
    val taskExpiryDate: Instant,
    val taskCompleted: Boolean
)
