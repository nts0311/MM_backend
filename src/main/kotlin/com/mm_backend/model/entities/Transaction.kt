package com.mm_backend.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

enum class TransactionType(val value: String) {
    INCOME("INCOME"),
    EXPENSE("EXPENSE")
}

@Entity
@JsonIgnoreProperties("hibernateLazyInitializer", "handler")
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
    var type: TransactionType?,
    var amount: Double = 0.0,
    var note: String = "",
    var date: Long = 0L,

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
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "wallet_id",
        referencedColumnName = "id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    var wallet: Wallet = Wallet()
) {
    val categoryId: Long
        get() = category.id

    val walletId: Long
        get() = wallet.id
}