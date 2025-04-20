package com.khcodings.tasky.infrastructure.persistence.refresh

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/*
* The @Indexed(expireAfter = "0s")
* annotation creates a TTL (time-to-live) index on the
*  expiresAt field in MongoDB. This automatically
* removes documents from the refresh_token collection as
* soon as the specified expiration date/time (expiresAt) is reached.
*  Thus, it's effectively used to ensure expired tokens don't persist in the database,
*  simplifying cleanup and enhancing security by automatically invalidating expired tokens.
*/

@Document(collection = "refresh_token")
data class RefreshToken(
    val userId: ObjectId,
    @Indexed(expireAfter = "0s") val expiresAt: Instant,
    val createdAt: Instant = Instant.now(),
    val hashedToken: String
)
