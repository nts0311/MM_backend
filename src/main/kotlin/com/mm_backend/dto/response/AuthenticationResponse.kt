package com.mm_backend.dto.response

class AuthenticationResponse(
    val jwtToken: String? = null,
    val userId: Long = 0L
)