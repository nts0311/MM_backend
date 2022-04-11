package com.mm_backend.controllers

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController

open class BaseController {
    val modelMapper = ModelMapper().apply {
        configuration.matchingStrategy = MatchingStrategies.STRICT
    }

    val username: String
        get() = SecurityContextHolder.getContext().authentication.principal as String

    val userId: Long
        get() = SecurityContextHolder.getContext().authentication.details as Long
}