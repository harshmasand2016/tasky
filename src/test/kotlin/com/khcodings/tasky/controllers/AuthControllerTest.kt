/*

package com.khcodings.tasky.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.khcodings.tasky.application.auth.AuthService
import com.khcodings.tasky.application.auth.AuthService.TokenPair
import com.khcodings.tasky.infrastructure.config.SecurityConfig
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@Import(AuthControllerTestConfig::class, SecurityConfig::class)
@WebMvcTest(AuthController::class)
class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var authService: AuthService

    @Test
    fun `should return token pair on successful login`() {
        // given
        val request = AuthController.AuthRequest(
            email = "user@example.com",
            password = "secure123"
        )

        val tokenPair = TokenPair(
            accessToken = "access-token-123",
            refreshToken = "refresh-token-456"
        )

        every { authService.login(request.email, request.password) } returns tokenPair

        // when + then
        val result = mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
            with(csrf())
        }.andExpect {
                status { isOk() }
                jsonPath("$.data.accessToken") { value("access-token-123") }
                jsonPath("$.data.refreshToken") { value("refresh-token-456") }
            }
            .andReturn()

        println("✅ Response body: ${result.response.contentAsString}")
    }


}


*/
/*import com.fasterxml.jackson.databind.ObjectMapper
import com.khcodings.tasky.application.auth.AuthService
import com.khcodings.tasky.infrastructure.config.SecurityConfig
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@Import(SecurityConfig::class)
@WebMvcTest(AuthController::class)
class AuthControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var authService: AuthService

    @Test
    fun `should return token pair on successful login`() {
        val request = AuthController.AuthRequest("user@example.com", "secure123")

        val tokenPair = AuthService.TokenPair(
            accessToken = "access-token-123",
            refreshToken = "refresh-token-456"
        )

        every { authService.login(request.email, request.password) } returns tokenPair

        val result = mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
            with(csrf())
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.data.accessToken") { value("access-token-123") }
                jsonPath("$.data.refreshToken") { value("refresh-token-456") }
            }
            .andReturn()

        println("✅ Response body: ${result.response.contentAsString}")
    }
}*//*
*/
/*


*//*

*/
/*
@Import(SecurityConfig::class) // ✅ Add this line
@WebMvcTest(AuthController::class)
class AuthControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @MockBean
    lateinit var authService: AuthService
    @MockBean
    lateinit var jwtService: JwtService
    @MockBean
    lateinit var jwtAuthFilter: JwtAuthFilter

    @MockBean
    lateinit var customAuthEntryPoint: CustomAuthEntryPoint


    @Test
    fun `should return token pair on successful login`(){
        // given
        val request = AuthController.AuthRequest(
            email = "user@example.com",
            password = "secure123"
        )

        val tokenPair = AuthService.TokenPair(
            accessToken = "access-token-123",
            refreshToken = "refresh-token-456"
        )

        org.mockito.Mockito.`when`(authService.login(request.email, request.password)).thenReturn(tokenPair)


        // when + then
        val result = mockMvc.post("/auth/login") {
            with(csrf())
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }*//*
*/
/*

*//*

*/
/*.andExpect {
            status { isOk() }
            jsonPath("$.data.accessToken") { value("access-token-123") }
            jsonPath("$.data.refreshToken") { value("refresh-token-456") }
        }*//*
*/
/*
*//*

*/
/*

            .andExpect {
                status { isOk() }
            }
            .andReturn()

        println("📦 Response body: ${result.response.contentAsString}")
    }
}*//*


*/
