package com.khcodings.tasky.infrastructure.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.khcodings.tasky.shared.response.ApiResponse
import com.khcodings.tasky.shared.response.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component


@Component
class CustomAuthEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorResponse = ApiResponse(
            data = null,
            errors = ErrorResponse(
                error_code = 401,
                error_description = "Unauthorized: ${authException.message}"
            )
        )
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(ObjectMapper().writeValueAsString(errorResponse))
    }
}