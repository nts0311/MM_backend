package com.mm_backend.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.InvalidClaimException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.mm_backend.model.entities.AppUser
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUtils {
    private val EXPRIRE_TIME = 5000
    private val verifier = JWT.require(Algorithm.HMAC256(SECRET.toByteArray())).build()

    fun extractUserId(token: String): Long {
        val decodedJwt = verifier.verify(token)
        return decodedJwt.claims["userId"]?.asLong() ?: throw InvalidClaimException("Cannot extract userId")
    }

    fun generateToken(user: AppUser): String {
        return JWT.create()
            .withSubject(user.username)
            .withClaim("userId", user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPRIRE_TIME * 60 * 1000))
            .sign(Algorithm.HMAC256(SECRET))
    }

    companion object {
        val SECRET = "AbCd123"
    }
}