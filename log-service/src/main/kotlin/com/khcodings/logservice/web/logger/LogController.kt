package com.khcodings.logservice.web.logger

import com.khcodings.logservice.domain.logger.LogRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/logger"])
class LogController {

    @PostMapping
    fun receiveLog(@RequestBody log: LogRequest): ResponseEntity<String> {
        println("📥 LOG: [${log.timestamp}] from [${log.source}]: ${log.message}")
        return ResponseEntity.ok("Log received")
    }

    @GetMapping
    fun healthCheck(): String {
        return "✅ Log Service is up and running!"
    }
}