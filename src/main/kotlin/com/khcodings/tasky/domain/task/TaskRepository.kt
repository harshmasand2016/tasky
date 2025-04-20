package com.khcodings.tasky.domain.task

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface TaskRepository : MongoRepository<Task, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<Task>
}