package com.mm_backend.repos

import com.mm_backend.model.entities.Transaction
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TransactionRepo : JpaRepository<Transaction, Long> {
    fun findTransactionByDateBetweenOrderByDateDesc(startDate: Long, endDate: Long, pageable: Pageable): List<Transaction>

    fun findTransactionsByWallet_IdAndDateBetweenOrderByDateDesc(
        walletId: Long,
        startDate: Long,
        endDate: Long,
        pageable: Pageable
    ): List<Transaction>
}