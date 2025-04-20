package com.khcodings.tasky.infrastructure.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component


@Component
class HashEncoder {

    private val bcrypt = BCryptPasswordEncoder()

    fun encode(rawPassword: String) = bcrypt.encode(rawPassword)

    fun matches(rawPassword: String, encodedPassword: String) = bcrypt.matches(rawPassword, encodedPassword)
}