package com.mm_backend.repos

import com.mm_backend.model.entities.TransactionCategory
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionCategoryRepo: JpaRepository<TransactionCategory, Long> {
    fun findAllByUserId(userId: Long): List<TransactionCategory>
}