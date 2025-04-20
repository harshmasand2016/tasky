package com.khcodings.tasky.web.advice

import com.khcodings.tasky.web.advice.exceptions.AuthException
import com.khcodings.tasky.shared.response.ApiResponse
import com.khcodings.tasky.shared.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatus(ex: ResponseStatusException): ResponseEntity<ApiResponse<Nothing>> {
        val error = ErrorResponse(
            error_code = ex.statusCode.value(),
            error_description = ex.reason ?: "Unexpected error"
        )
        println("🔥 GlobalExceptionHandler caught: ${ex.message}")

        return ResponseEntity.status(ex.statusCode)
            .body(ApiResponse(errors = error))
    }

    @ExceptionHandler(AuthException::class)
    fun handleAuthException(ex: AuthException): ResponseEntity<ApiResponse<Nothing>> {
        val error = ErrorResponse(
            error_code = 422,
            error_description = ex.message ?: "Authentication failed"
        )
        println("🔥 GlobalExceptionHandler caught AuthException: ${ex.message}")
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse(errors = error))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val firstError = ex.bindingResult
            .fieldErrors
            .sortedBy { it.field } // enforce consistent order
            .firstOrNull()

        val errorMessage = if (firstError != null) {
            "${firstError.field}: ${firstError.defaultMessage}"
        } else {
            "Invalid request"
        }

        println("🔥 Validation failed on field '${firstError?.field}': ${firstError?.defaultMessage}")


        val error = ErrorResponse(
            error_code = 422,
            error_description = errorMessage
        )

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse(errors = error))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArg(ex: IllegalArgumentException): ResponseEntity<ApiResponse<Nothing>> {
        val error = ErrorResponse(
            error_code = 400,
            error_description = ex.message ?: "Bad request"
        )
        println("🔥 GlobalExceptionHandler caught: ${ex.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse(errors = error))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        ex.printStackTrace()
        val error = ErrorResponse(
            error_code = 500,
            error_description = "Something went wrong on the server. ${ex.message}"
        )
        println("🔥 GlobalExceptionHandler caught: ${ex.message}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse(errors = error))
    }
}
