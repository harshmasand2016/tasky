package com.khcodings.tasky.web.task

import com.khcodings.tasky.service.tasks.TaskService
import com.khcodings.tasky.service.tasks.dto.TaskRequest
import com.khcodings.tasky.service.tasks.dto.TaskResponse
import com.khcodings.tasky.shared.response.ActionResponse
import com.khcodings.tasky.shared.response.ApiResponse
import com.khcodings.tasky.shared.response.SuccessResponse
import jakarta.validation.Valid
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/tasks")
class TasksController(
    private val taskService: TaskService,
) {

    @PostMapping
    fun saveTask(
        @Valid @RequestBody request: TaskRequest
    ): ResponseEntity<ApiResponse<SuccessResponse<TaskResponse>>> {
        val ownerID = SecurityContextHolder.getContext().authentication.principal as String
        val task = taskService.saveTask(ownerID, request)

        val response = SuccessResponse(
            data = task,
            actionResponse = ActionResponse(
                response_code = 200,
                response_message = "Task Saved Successfully"
            )
        )
        return ResponseEntity.ok(ApiResponse(data = response))
    }

    @GetMapping
    fun getAllTasks(): ResponseEntity<ApiResponse<List<TaskResponse>>> {
        val ownerID = getCurrentUserId()
        val tasks = taskService.getAllTasks(ownerID)
        return ResponseEntity.ok(ApiResponse(data = tasks))
    }

    private fun getCurrentUserId(): String {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated")

        return when (val principal = authentication.principal) {
            is String -> {
                if (!ObjectId.isValid(principal)) {
                    throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid UserID")
                }
                principal
            }

            else -> throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid UserID - 101")
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable(value = "id") id: String): ResponseEntity<ApiResponse<ActionResponse>> {
        val ownerID = getCurrentUserId()
        taskService.deleteTask(ownerID, id)

        return ResponseEntity.ok(
            ApiResponse(
                data = ActionResponse(
                    response_code = 200,
                    response_message = "Successfully deleted the task"
                )
            )
        )
    }
}