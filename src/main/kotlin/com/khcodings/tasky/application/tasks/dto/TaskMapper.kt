package com.khcodings.tasky.application.tasks.dto

import com.khcodings.tasky.domain.task.Task

fun Task.toTaskResponse(): TaskResponse {
    return TaskResponse(
        id = id.toHexString(),
        taskName = title,
        taskContent = content,
        taskColor = color,
        taskCreatedDate = createdAt
    )
}