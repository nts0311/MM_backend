package com.mm_backend.controllers

import com.mm_backend.constants.MSG_USER_DOES_NOT_OWN_THIS_WALLET
import com.mm_backend.constants.MSG_USER_NOT_EXIST
import com.mm_backend.dto.request.CreateWalletRequest
import com.mm_backend.dto.request.UpdateWalletRequest
import com.mm_backend.model.entities.AppUser
import com.mm_backend.model.entities.Wallet
import com.mm_backend.services.UserService
import com.mm_backend.services.WalletService
import com.mm_backend.utils.JwtUtils
import com.mm_backend.utils.badRequest
import com.mm_backend.utils.ok
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallet")
class WalletController @Autowired constructor(
    val jwtUtils: JwtUtils,
    val walletService: WalletService,
    val userService: UserService
): BaseController() {
    @PostMapping("create")
    fun createWallet(@RequestBody createWalletRequest: CreateWalletRequest): ResponseEntity<*> {
        if (!userService.isUserExist(createWalletRequest.userId))
            return badRequest(MSG_USER_NOT_EXIST)

        val newWallet = modelMapper.map(createWalletRequest, Wallet::class.java)
        newWallet.user = AppUser(id = userId)
        walletService.createWallet(newWallet)
        return ok()
    }

    @PostMapping("update")
    fun updateWallet(@RequestBody request: UpdateWalletRequest): ResponseEntity<*> {
        if (!userService.isUserExist(request.userId))
            return badRequest(MSG_USER_NOT_EXIST)

        if(!userOwnWallet(request.walletId))
            return badRequest(MSG_USER_DOES_NOT_OWN_THIS_WALLET)

        val wallet = modelMapper.map(request, Wallet::class.java)
        wallet.id = request.walletId
        wallet.user = AppUser(id = userId)
        walletService.updateWallet(wallet)
        return ok()
    }

    @DeleteMapping("delete")
    fun deleteWallet(@RequestParam("walletId") walletId: Long): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        if(!userOwnWallet(walletId))
            return badRequest(MSG_USER_DOES_NOT_OWN_THIS_WALLET)

        walletService.deleteWallet(walletId)
        return ok()
    }

    @GetMapping("list")
    fun getUserWallets(): ResponseEntity<*> {
        if (!userService.isUserExist(userId))
            return badRequest(MSG_USER_NOT_EXIST)

        return ok(walletService.getWalletListOfUser(userId))
    }

    private fun userOwnWallet(walletId: Long): Boolean {
        return walletService.walletOwnedByUser(walletId, username)
    }
}