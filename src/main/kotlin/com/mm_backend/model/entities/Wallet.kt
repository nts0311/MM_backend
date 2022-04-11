package com.mm_backend.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Wallet(
    @Id
    @SequenceGenerator(
        name = "wallet_sequence",
        sequenceName = "wallet_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "wallet_sequence"
    )
    var id:Long = 0,
    var name:String = "",
    var imageUrl: String = "",
    var amount: Long = 0L,
    var currency: String = "",

    @JsonIgnore
    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    var user: AppUser = AppUser()
) {
}