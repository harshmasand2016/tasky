package com.khcodings.tasky.service.auth

import com.khcodings.tasky.service.auth.dto.TokenPair
import com.khcodings.tasky.shared.response.ActionResponse

interface AuthService {
    fun register(email: String, password: String) : ActionResponse
    fun login(email: String, password: String) : TokenPair?
    fun refresh(refreshToken: String) : TokenPair
}