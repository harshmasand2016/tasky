package com.khcodings.tasky.service.tasks

import com.khcodings.tasky.domain.task.Task
import com.khcodings.tasky.domain.task.TaskRepository
import com.khcodings.tasky.service.tasks.dto.TaskRequest
import com.khcodings.tasky.service.tasks.dto.TaskResponse
import com.khcodings.tasky.service.tasks.dto.toTaskResponse
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository
) : TaskService{
    override fun saveTask(ownerId: String, request: TaskRequest): TaskResponse {
        val task = Task(
            id = request.id?.let { ObjectId(it) } ?: ObjectId.get(),
            title = request.taskName,
            content = request.taskContent,
            color = request.taskColor,
            expiryAt = request.taskExpiryDate,
            isCompleted = request.taskCompleted,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            ownerId = ObjectId(ownerId),
        )

        return taskRepository.save(task).toTaskResponse()
    }

    override fun getAllTasks(ownerId: String): List<TaskResponse> {
        return taskRepository.findByOwnerId(ObjectId(ownerId)).map { it.toTaskResponse() }
    }

    override fun deleteTask(ownerId: String, taskId: String) {
        val task = taskRepository.findById(ObjectId(taskId)).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "No Task found!")
        }

        if (task.ownerId.toHexString() != ownerId) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this task")
        }

        taskRepository.deleteById(ObjectId(taskId))
    }
}