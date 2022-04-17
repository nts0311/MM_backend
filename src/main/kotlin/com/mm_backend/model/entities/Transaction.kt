package com.mm_backend.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

enum class TransactionType(val value: String) {
    INCOME("Income"),
    EXPENSE("Expense")
}

@Entity
class Transaction(
    @Id
    @SequenceGenerator(
        name = "transaction_sequence",
        sequenceName = "transaction_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "transaction_sequence"
    )
    var id: Long = 0,
    @Enumerated(EnumType.STRING)
    var type: TransactionType,
    var amount: Long,
    var note: String,
    var date: Long,

    @JsonIgnore
    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "category_id",
        referencedColumnName = "id"
    )
    var category: TransactionCategory = TransactionCategory(),

    @JsonIgnore
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    @JoinColumn(
        name = "wallet_id",
        referencedColumnName = "id"
    )
    var wallet: Wallet = Wallet()
) {
    val categoryId: Long
        get() = category.id
}