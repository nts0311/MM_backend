package com.mm_backend.dto.request

data class DeleteTransactionRequest(
    var transactionId: Long,
    var walletId: Long,
    var amount: Double
)
