package com.khcodings.tasky.service.tasks.dto

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