package com.khcodings.tasky.service.tasks.dto

import java.time.Instant

data class TaskResponse(
    val id: String,
    val taskName: String,
    val taskContent: String,
    val taskColor: Long,
    val taskCreatedDate: Instant,
)
