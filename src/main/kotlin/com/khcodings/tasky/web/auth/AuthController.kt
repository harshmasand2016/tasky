package com.khcodings.tasky.web.auth

import com.khcodings.tasky.application.auth.AuthService
import com.khcodings.tasky.application.auth.dto.AuthRequest
import com.khcodings.tasky.application.auth.dto.RefreshRequest
import com.khcodings.tasky.application.auth.dto.TokenPair
import com.khcodings.tasky.shared.response.ActionResponse
import com.khcodings.tasky.shared.response.ApiResponse
import com.khcodings.tasky.shared.interfaces.ValidationGroups
import jakarta.validation.Valid
import jakarta.validation.groups.Default
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody
                 @Validated(value = [ValidationGroups.NotBlankGroup::class, ValidationGroups.FormatGroup::class, Default::class])
                 authRequest: AuthRequest
    ): ResponseEntity<ApiResponse<ActionResponse>> {
        val actionResponse = authService.register(authRequest.email, authRequest.password)
        return ResponseEntity.ok(ApiResponse(data = actionResponse))
    }

    @PostMapping("/login")
    fun login(
              @RequestBody
              @Validated(value = [ValidationGroups.NotBlankGroup::class, ValidationGroups.FormatGroup::class, Default::class])
              authRequest: AuthRequest): ResponseEntity<ApiResponse<TokenPair>> {
        val tokenPair = authService.login(authRequest.email, authRequest.password)
        return ResponseEntity.ok(ApiResponse(data = tokenPair))
    }

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody refreshRequest: RefreshRequest): ResponseEntity<ApiResponse<TokenPair>> {
        val tokenPair = authService.refresh(refreshRequest.refreshToken)
        return ResponseEntity.ok(ApiResponse(data = tokenPair))
    }
}