package com.mm_backend.controllers

import com.mm_backend.constants.*
import com.mm_backend.dto.request.CreateTransactionRequest
import com.mm_backend.dto.request.GetTransactionBetweenDateRequest
import com.mm_backend.model.entities.Transaction
import com.mm_backend.model.entities.TransactionCategory
import com.mm_backend.model.entities.TransactionType
import com.mm_backend.model.entities.Wallet
import com.mm_backend.utils.badRequest
import com.mm_backend.utils.ok
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("transaction")
class TransactionController: BaseController() {
    @PostMapping("create")
    fun createTransaction(@RequestBody body: CreateTransactionRequest): ResponseEntity<*>{
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        if(!userOwnWallet(body.walletId))
            return badRequest(MSG_USER_DOES_NOT_OWN_THIS_WALLET)

        if(!isCategoryExist(body.categoryId))
            return badRequest(MSG_CATEGORY_INVALID)

        if(body.type != TransactionType.EXPENSE.value && body.type != TransactionType.INCOME.value)
            return badRequest(MSG_INVALID_TRANSACTION_TYPE)

        val transaction = modelMapper.map(body, Transaction::class.java)
        transaction.wallet = Wallet(id = body.walletId)
        transaction.category = TransactionCategory(id = body.categoryId)
        transaction.type = TransactionType.valueOf(body.type)

        transactionService.createTransaction(transaction)

        return ok()
    }

    @PostMapping("get-between-date")
    fun getTransactionsBetweenDate(@RequestBody body:GetTransactionBetweenDateRequest): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        if(body.walletId != null && !userOwnWallet(body.walletId!!))
            return badRequest(MSG_USER_DOES_NOT_OWN_THIS_WALLET)

        val transactions = transactionService.getTransactionsBetweenDate(body.walletId, body.startDate, body
            .endDate, body.page, body.size)

        return ok(mutableMapOf<Any, Any>(
            "page" to body.page,
            "size" to body.size,
            "data" to transactions
        ))
    }

    fun isCategoryExist(categoryId: Long) = categoryService.isCategoryExist(categoryId)
}