package com.khcodings.tasky.controllers

import com.khcodings.tasky.infrastructure.security.CustomAuthEntryPoint
import com.khcodings.tasky.application.auth.AuthService
import com.khcodings.tasky.infrastructure.security.JwtAuthFilter
import com.khcodings.tasky.infrastructure.security.JwtService
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class AuthControllerTestConfig {

    @Bean
    fun authService(): AuthService = mockk(relaxed = true)

    @Bean
    fun jwtService(): JwtService = mockk(relaxed = true)

    // You can construct the JwtAuthFilter with the mock jwtService
    @Bean
    fun jwtAuthFilter(jwtService: JwtService): JwtAuthFilter = JwtAuthFilter(jwtService)

    @Bean
    fun customAuthEntryPoint(): CustomAuthEntryPoint = mockk(relaxed = true)

}
