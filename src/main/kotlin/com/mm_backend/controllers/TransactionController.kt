package com.mm_backend.controllers

import com.mm_backend.constants.MSG_USER_DOES_NOT_OWN_THIS_WALLET
import com.mm_backend.constants.MSG_USER_NOT_EXIST
import com.mm_backend.dto.request.CreateTransactionRequest
import com.mm_backend.model.entities.Transaction
import com.mm_backend.model.entities.Wallet
import com.mm_backend.services.TransactionService
import com.mm_backend.services.UserService
import com.mm_backend.utils.badRequest
import com.mm_backend.utils.ok
import org.springframework.beans.factory.annotation.Autowired
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

        //check category

        val transaction = modelMapper.map(body, Transaction::class.java)
        transaction.wallet = Wallet(id = body.walletId)
        transactionService.createTransaction(transaction)

        return ok()
    }
}