package com.khcodings.tasky.infrastructure.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.khcodings.tasky.shared.response.ApiResponse
import com.khcodings.tasky.shared.response.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.takeIf { it.startsWith("Bearer ") }
            ?.removePrefix("Bearer ")?.trim()
        if (token != null) {
            try {
                if (jwtService.validateAccessToken(token)) {
                    val userId = jwtService.getUserIdFromToken(token)
                    val authentication = UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        emptyList() // or use roles if needed
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                    filterChain.doFilter(request, response)
                } else {
                    sendUnauthorizedResponse(response, "Expired Token, Please login again", "999")
                }
            } catch (e: Exception) {
                println("Exception occurred: ${e.message}")
                sendUnauthorizedResponse(response)
            }
        } else {
            sendUnauthorizedResponse(response, "Please Add Authorization Token", "888")
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        /*val path = request.servletPath
        return path.startsWith("/auth/")*/
        val uri = request.requestURI  // Use requestURI instead of servletPath
        println("DEBUG: request.requestURI = $uri") // Optional: log to verify the URI in tests
        return uri.startsWith("/auth/") ||
                uri.startsWith("/v3/api-docs") ||
                uri.startsWith("/swagger-ui")
    }

    private fun sendUnauthorizedResponse(
        response: HttpServletResponse,
        message: String? = null,
        responseCode: String? = null
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val errorMessage = message ?: "Unauthorized: Full authentication is required to access this resource"
        val responseCodeValue = responseCode ?: HttpStatus.UNAUTHORIZED.value().toString()

        val errorResponse = ApiResponse(
            data = null,
            errors = ErrorResponse(
                error_code = responseCodeValue.toInt(),
                error_description = errorMessage
            )
        )
        response.writer.write(ObjectMapper().writeValueAsString(errorResponse))
    }



}