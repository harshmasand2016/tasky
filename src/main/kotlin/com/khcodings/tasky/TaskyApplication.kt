package com.khcodings.tasky

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskyApplication

fun main(args: Array<String>) {
	runApplication<TaskyApplication>(*args)
}
