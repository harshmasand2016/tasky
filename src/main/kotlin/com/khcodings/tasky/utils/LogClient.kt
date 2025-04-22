package com.khcodings.tasky.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.Instant

@Component
class LogClient(
    private val restTemplate: RestTemplate,
    @Value("\${log.service.base-url}")
    private val logServiceUrl: String
) {
    data class LogPayload(
        val source: String,
        val message: String,
        val timestamp: Instant = Instant.now()
    )

    fun sendLog(source: String, message: String) {
        println("🟡 LogClient is trying to send log to log-service...")

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val request = HttpEntity(LogPayload(source, message), headers)

        try {
            restTemplate.postForEntity("${logServiceUrl}/logger", request, String::class.java)
            println("✅ Log sent successfully: $message")

        } catch (ex: Exception) {
            ex.printStackTrace()
            println("⚠️ Failed to send log to log-service: ${ex.message}")
        }
    }
}
