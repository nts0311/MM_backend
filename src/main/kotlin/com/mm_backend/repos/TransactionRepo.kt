package com.mm_backend.repos

import com.mm_backend.model.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepo: JpaRepository<Transaction, Long> {
}