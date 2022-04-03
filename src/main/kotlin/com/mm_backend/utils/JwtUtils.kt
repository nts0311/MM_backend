package com.mm_backend.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mm_backend.model.entities.AppUser
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUtils {
    private val EXPRIRE_TIME = 60

    fun generateToken(user: AppUser): String {
        return JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPRIRE_TIME * 60 * 1000))
            .sign(Algorithm.HMAC256(SECRET))
    }

    companion object {
        val SECRET = "AbCd123"
    }
}