package com.mm_backend.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.mm_backend.constants.Constants
import javax.persistence.*

enum class CategoryType(val value: String) {
    INCOME("Income"),
    EXPENSE("Expense")
}

@Entity
class TransactionCategory(
    @Id
    @SequenceGenerator(
        name = "category_sequence",
        sequenceName = "category_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "category_sequence"
    )
    var id: Long = 0,
    var name: String = "",
    @Enumerated(EnumType.STRING)
    var type: CategoryType = CategoryType.EXPENSE,
    var imageUrl: String = "",

    @JsonIgnore
    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "parent_category_id",
        referencedColumnName = "id"
    )
    var parentCategory: TransactionCategory? = null,

//    @JsonIgnore
//    @OneToMany(mappedBy = "parentCategory")
//    var childCategories: MutableSet<TransactionCategory> = mutableSetOf(),

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
    val parentId: Long
        get() = parentCategory?.id ?: id
}