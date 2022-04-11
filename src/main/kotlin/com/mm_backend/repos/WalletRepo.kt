package com.mm_backend.repos

import com.mm_backend.model.entities.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface WalletRepo: JpaRepository<Wallet, Long> {
    fun findByUserUsername(username: String): List<Wallet>

    fun findByUserId(userId: Long): List<Wallet>

    @Query(
        value = "SELECT COUNT(*) FROM wallet w, app_user u WHERE w.id = :walletId AND u.username = :username",
        nativeQuery = true
    )
    fun walletBelongToUser(walletId: Long, username: String): Int
}