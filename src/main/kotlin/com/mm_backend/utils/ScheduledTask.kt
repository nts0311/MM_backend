package com.mm_backend.utils

import com.mm_backend.services.FcmService
import com.mm_backend.services.UserService
import com.mm_backend.services.WalletService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledTask {
    @Autowired lateinit var fcmService: FcmService
    @Autowired lateinit var walletService: WalletService
    @Autowired lateinit var userService: UserService

    @Scheduled(fixedDelay = 120*1000)
    fun warnUserLowMoney() {
        val users = userService.getAllUser()
        for (user in users) {
            val walletList = walletService.getWalletListOfUser(user.id)

            for(wallet in walletList) {
                if (wallet.amount < 0) {
                    fcmService.sendNotification(user.fcmToken)
                    break
                }
            }
        }
    }
}

