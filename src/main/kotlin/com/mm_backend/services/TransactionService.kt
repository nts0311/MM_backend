package com.mm_backend.services

import com.mm_backend.model.entities.Transaction
import com.mm_backend.repos.TransactionRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionService @Autowired constructor(
    private val transactionRepo: TransactionRepo
) {
    fun createTransaction(transaction: Transaction){
        transactionRepo.save(transaction)
    }
}