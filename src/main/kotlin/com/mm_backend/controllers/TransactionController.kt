package com.mm_backend.controllers

import com.mm_backend.constants.*
import com.mm_backend.dto.request.CreateTransactionRequest
import com.mm_backend.dto.request.DeleteTransactionRequest
import com.mm_backend.dto.request.GetTransactionBetweenDateRequest
import com.mm_backend.dto.request.UpdateTransactionRequest
import com.mm_backend.model.entities.Transaction
import com.mm_backend.model.entities.TransactionCategory
import com.mm_backend.model.entities.TransactionType
import com.mm_backend.model.entities.Wallet
import com.mm_backend.utils.badRequest
import com.mm_backend.utils.ok
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

        val transaction = modelMapper.map(body, Transaction::class.java).apply {
            wallet = Wallet(id = body.walletId)
            category = TransactionCategory(id = body.categoryId)
            type = TransactionType.valueOf(body.type)
        }

        transactionService.createTransaction(transaction)

        return ok()
    }

    @PostMapping("get-between-date-of-wallet")
    fun getTransactionsBetweenDateOfWallet(@RequestBody body:GetTransactionBetweenDateRequest): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        if(body.walletId != null && !userOwnWallet(body.walletId!!))
            return badRequest(MSG_USER_DOES_NOT_OWN_THIS_WALLET)

        val transactions = transactionService.getTransactionsBetweenDateOfWallet(body.walletId, body.startDate, body
            .endDate, body.page, body.size)

        return ok(mutableMapOf<Any, Any>(
            "page" to body.page,
            "size" to transactions.size,
            "data" to transactions
        ))
    }

    @GetMapping("get-by-id")
    fun getTransactionsById(@RequestParam transactionId: Long): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        val transaction = transactionService.getTransactionById(transactionId)

        return ok(transaction)
    }

    @GetMapping("get-between-date")
    fun getTransactionsBetweenDate(@RequestParam start: Long, @RequestParam end: Long): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        val transactions = transactionService.getTransactionBetweenDate(start, end)

        return ok(mutableMapOf<Any, Any>(
            "page" to 0,
            "size" to transactions.size,
            "data" to transactions
        ))
    }

    @PostMapping("update")
    fun updateTransaction(@RequestBody body:UpdateTransactionRequest): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        if(!userOwnWallet(body.walletId))
            return badRequest(MSG_USER_DOES_NOT_OWN_THIS_WALLET)

        if(!transactionService.isTransactionExisted(body.id))
            return badRequest(MSG_INVALID_TRANSACTION)

        if(!isCategoryExist(body.categoryId))
            return badRequest(MSG_CATEGORY_INVALID)

        if(body.type != TransactionType.EXPENSE.value && body.type != TransactionType.INCOME.value)
            return badRequest(MSG_INVALID_TRANSACTION_TYPE)

        val transaction = modelMapper.map(body, Transaction::class.java).apply {
            wallet = Wallet(id = body.walletId)
            category = TransactionCategory(id = body.categoryId)
            type = TransactionType.valueOf(body.type)
        }

        transactionService.updateTransaction(transaction)

        return ok()
    }

    @PostMapping("delete")
    fun deleteTransaction(@RequestBody body: DeleteTransactionRequest): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        if(!transactionService.isTransactionExisted(body.transactionId))
            return badRequest(MSG_INVALID_TRANSACTION)

        transactionService.deleteTransaction(body.transactionId)

        return ok()
    }

    fun isCategoryExist(categoryId: Long) = categoryService.isCategoryExist(categoryId)
}