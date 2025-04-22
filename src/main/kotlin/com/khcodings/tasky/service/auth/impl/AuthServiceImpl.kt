package com.khcodings.tasky.service.auth.impl

import com.khcodings.tasky.domain.user.User
import com.khcodings.tasky.domain.user.UserRepository
import com.khcodings.tasky.infrastructure.persistence.refresh.RefreshToken
import com.khcodings.tasky.infrastructure.persistence.refresh.RefreshTokenRepository
import com.khcodings.tasky.infrastructure.security.HashEncoder
import com.khcodings.tasky.infrastructure.security.JwtService
import com.khcodings.tasky.service.auth.AuthService
import com.khcodings.tasky.service.auth.dto.TokenPair
import com.khcodings.tasky.shared.response.ActionResponse
import com.khcodings.tasky.utils.LogClient
import com.khcodings.tasky.web.advice.exceptions.AuthException
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.security.MessageDigest
import java.time.Instant
import java.util.*

@Service
class AuthServiceImpl(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val logClient: LogClient
) : AuthService{


    override fun register(email: String, password: String): ActionResponse {
        val user = userRepository.findByEmail(email.trim())
        if(user != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "A user with that email already exists.")
        }
        userRepository.save(
            User(
                email = email,
                hashedPassword = hashEncoder.encode(password),
            )
        )
        return ActionResponse(
            response_code = HttpStatus.CREATED.value() ,
            response_message = "User registered successfully"
        )
    }

    override fun login(email: String, password: String) : TokenPair?{
        logClient.sendLog("tasky-auth", "Login Hit")
        val user = userRepository.findByEmail(email)
            ?: throw AuthException("This user does not exist. Kindly register")

        if(!hashEncoder.matches(password, user.hashedPassword)) {
            logClient.sendLog("tasky-auth", "Login failed for $email ❌")
            throw AuthException("Invalid Credentials, Kindly use valid Credentials!")
        }
        logClient.sendLog("tasky-auth", "User $email logged in ✅")

        return generateTokenPair(userId = user.id.toHexString())
    }

    fun storeRefreshToken(userId: ObjectId, rawRefreshToken: String) {
        val hashedToken = hashToken(rawRefreshToken)
        val expiryMs = jwtService.refreshTokenValidityMs
        val expiryAt = Instant.now().plusMillis(expiryMs)
        refreshTokenRepository.save(
            RefreshToken(
                userId = userId,
                expiresAt = expiryAt,
                hashedToken = hashedToken
            )
        )
    }

    /**
     * The provided method safely renews a user's login tokens.
     * It validates the refresh token, verifies user existence,
     * checks the token against stored records, and deletes it upon successful
     * validation to prevent reuse.
     * It then generates and stores new access and refresh tokens,
     * returning them to the user.
     * The @Transactional annotation ensures all these database operations
     * either fully succeed or fully fail together, maintaining consistency
     * and preventing security risks caused by partial database updates or token misuse.
     */
    @Transactional
    override fun refresh(refreshToken: String) : TokenPair {
        if(!jwtService.validateRefreshToken(refreshToken)){
            println("⛔ Invalid or expired token: $refreshToken")

            throw ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid or expired refresh token.")
        }

        val userID = jwtService.getUserIdFromToken(refreshToken)
        println("✅ Extracted userId from token: $userID")


        val user = userRepository.findById(ObjectId(userID)).orElseThrow {
            println("InValid")
            ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid refresh token")
        }

        val hashed = hashToken(refreshToken)
        val refreshRecord = refreshTokenRepository.findByUserIdAndHashedToken(user.id, hashed)
        if (refreshRecord == null) {
            println("⛔ Token has already been used or does not exist in DB")
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token not recognized (may be used or expired)")
        }

        refreshTokenRepository.deleteByUserIdAndHashedToken(user.id, hashed)
        println("✅ Token valid, issuing new token pair")


        return generateTokenPair(userID)
    }

    fun generateTokenPair(userId: String): TokenPair {
        val accessToken = jwtService.generateAccessToken(userId)
        val refreshToken = jwtService.generateRefreshToken(userId)
        storeRefreshToken(ObjectId(userId), refreshToken)
        return TokenPair(accessToken, refreshToken)
    }

    fun hashToken(rawToken: String) : String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(rawToken.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}