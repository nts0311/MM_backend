package com.mm_backend.dto.request

data class UpdateTransactionRequest(
    var id: Long,
    var categoryId: Long,
    var walletId: Long,
    var type: String,
    var amount: Long,
    var note: String,
    var date: Long
)