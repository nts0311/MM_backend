package com.mm_backend.controllers

import com.mm_backend.services.*
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController

open class BaseController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var transactionService: TransactionService
    @Autowired
    lateinit var walletService: WalletService
    @Autowired
    lateinit var categoryService: TransactionCategoryService
    @Autowired
    lateinit var fcmService: FcmService

    val modelMapper = ModelMapper().apply {
        configuration.matchingStrategy = MatchingStrategies.STRICT
    }

    val username: String
        get() = SecurityContextHolder.getContext().authentication.principal as String

    val userId: Long
        get() = SecurityContextHolder.getContext().authentication.details as Long

    fun userOwnWallet(walletId: Long): Boolean {
        return walletService.walletOwnedByUser(walletId, username)
    }
}