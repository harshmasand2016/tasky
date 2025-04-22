package com.khcodings.tasky.service.tasks

import com.khcodings.tasky.service.tasks.dto.TaskRequest
import com.khcodings.tasky.service.tasks.dto.TaskResponse

interface TaskService{
    fun saveTask(ownerId: String, request: TaskRequest): TaskResponse
    fun getAllTasks(ownerId: String): List<TaskResponse>
    fun deleteTask(ownerId: String, taskId: String)
}