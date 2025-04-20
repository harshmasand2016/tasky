package com.khcodings.tasky.domain.task

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "tasks")
data class Task(
    val title: String,
    val content: String,
    val color: Long,
    val isCompleted: Boolean,
    val ownerId: ObjectId,
    @Id val id: ObjectId = ObjectId.get(),
    val createdAt: Instant,
    val updatedAt: Instant,
    val expiryAt: Instant,
)
