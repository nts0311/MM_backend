package com.mm_backend.services

import com.mm_backend.model.entities.Transaction
import com.mm_backend.model.entities.TransactionType
import com.mm_backend.repos.TransactionRepo
import com.mm_backend.repos.WalletRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService @Autowired constructor(
    private val transactionRepo: TransactionRepo,
    private val walletRepo: WalletRepo
) {
    @Transactional
    fun createTransaction(transaction: Transaction) {
        transactionRepo.save(transaction)
        val wallet = walletRepo.getById(transaction.wallet.id)

        if (transaction.type == TransactionType.EXPENSE) {
            wallet.amount -= transaction.amount
        } else {
            wallet.amount += transaction.amount
        }

        walletRepo.save(wallet)
    }

    fun getTransactionsBetweenDateOfWallet(walletId: Long?, startDate: Long, endDate: Long, page: Int, size: Int): List<Transaction> {
        val page = PageRequest.of(page, size)
        return if (walletId != null) transactionRepo.findTransactionsByWallet_IdAndDateBetweenOrderByDateDesc(
            walletId,
            startDate,
            endDate,
            page
        )
        else transactionRepo.findTransactionByDateBetweenOrderByDateDesc(startDate, endDate, page)
    }

    fun getTransactionBetweenDate(startDate: Long, endDate: Long) : List<Transaction> {
        return transactionRepo.findTransactionByDateBetweenOrderByDateDesc(startDate, endDate, PageRequest.of(0, 10000))
    }

    fun getTransactionById(id: Long) = transactionRepo.getById(id)

    fun isTransactionExisted(transactionId: Long) = transactionRepo.existsById(transactionId)

    fun updateTransaction(transaction: Transaction) {
        transactionRepo.saveAndFlush(transaction)
    }

    @Transactional
    fun deleteTransaction(transaction: Transaction) {
        transactionRepo.deleteById(transaction.id)

        val wallet = walletRepo.getById(transaction.wallet.id)

        if (transaction.type == TransactionType.EXPENSE) {
            wallet.amount += transaction.amount
        } else {
            wallet.amount -= transaction.amount
        }

        walletRepo.save(wallet)
    }
}