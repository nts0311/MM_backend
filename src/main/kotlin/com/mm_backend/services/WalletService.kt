package com.mm_backend.services

import com.mm_backend.model.entities.Wallet
import com.mm_backend.repos.WalletRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WalletService {
    @Autowired
    lateinit var walletRepo: WalletRepo

    fun createWallet(wallet: Wallet) {
        walletRepo.save(wallet)
    }

    fun updateWallet(wallet: Wallet) {
        walletRepo.save(wallet)
    }

    fun deleteWallet(id: Long) {
        walletRepo.deleteById(id)
    }

    fun getWalletListOfUser(userId: Long): List<Wallet> {
        return walletRepo.findByUserId(userId)
    }

    fun getWalletById(id: Long): Wallet? {
        val walletOpt = walletRepo.findById(id)
        return if(walletOpt.isEmpty) null
        else walletOpt.get()
    }

    fun walletOwnedByUser(walletId: Long, username: String): Boolean
        = walletRepo.walletBelongToUser(walletId, username) == 1
}