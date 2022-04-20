package com.mm_backend.services

import com.mm_backend.constants.CategoryTitle
import com.mm_backend.constants.Constants
import com.mm_backend.model.entities.AppUser
import com.mm_backend.model.entities.CategoryType
import com.mm_backend.model.entities.TransactionCategory
import com.mm_backend.repos.TransactionCategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionCategoryService @Autowired constructor(
    private val categoryRepo: TransactionCategoryRepo
) {

    fun getCategoriesForUser(userId: Long) = categoryRepo.findAllByUserId(userId)

    fun isCategoryExist(id: Long) = categoryRepo.existsById(id)

    fun insertDefaultCategoriesForUser(user: AppUser) {
        val categories = mutableListOf<TransactionCategory>()

        val food = TransactionCategory(name = CategoryTitle.FOOD_AND_BEVERAGE, imageUrl = "")
        categories.add(food)
        categories.add(TransactionCategory(name = CategoryTitle.RESTAURANTS, imageUrl = "", parentCategory = food))
        categories.add(TransactionCategory(name = CategoryTitle.CAFE, imageUrl = "", parentCategory = food))

        val bill = TransactionCategory(name = CategoryTitle.BILL_AND_UTILITIES, imageUrl = "")
        categories.add(bill)
        categories.add(TransactionCategory(name = CategoryTitle.RENTALS, imageUrl = "", parentCategory = bill))
        categories.add(TransactionCategory(name = CategoryTitle.PHONE, imageUrl = "", parentCategory = bill))
        categories.add(TransactionCategory(name = CategoryTitle.WATER, imageUrl = "", parentCategory = bill))
        categories.add(TransactionCategory(name = CategoryTitle.ELECTRICITY, imageUrl = "", parentCategory = bill))
        categories.add(TransactionCategory(name = CategoryTitle.GAS, imageUrl = "", parentCategory = bill))
        categories.add(TransactionCategory(name = CategoryTitle.TELEVISION, imageUrl = "", parentCategory = bill))
        categories.add(TransactionCategory(name = CategoryTitle.INTERNET, imageUrl = "", parentCategory = bill))

        val transportation = TransactionCategory(name = CategoryTitle.TRANSPORTATION, imageUrl = "")
        categories.add(transportation)
        categories.add(TransactionCategory(name = CategoryTitle.PETROL, imageUrl = "", parentCategory = transportation))
        categories.add(TransactionCategory(name = CategoryTitle.PARKING_FEES, imageUrl = "", parentCategory = transportation))
        categories.add(TransactionCategory(name = CategoryTitle.MAINTENANCE, imageUrl = "", parentCategory = transportation))
        categories.add(TransactionCategory(name = CategoryTitle.TAXI, imageUrl = "", parentCategory = transportation))

        val shopping = TransactionCategory(name = CategoryTitle.SHOPPING, imageUrl = "")
        categories.add(shopping)
        categories.add(TransactionCategory(name = CategoryTitle.CLOTHING, imageUrl = "", parentCategory = shopping))
        categories.add(TransactionCategory(name = CategoryTitle.ELECTRONICS, imageUrl = "", parentCategory = shopping))
        categories.add(TransactionCategory(name = CategoryTitle.FOOTWEAR, imageUrl = "", parentCategory = shopping))
        categories.add(TransactionCategory(name = CategoryTitle.ACCESSORIES, imageUrl = "", parentCategory = shopping))

        categories.add(TransactionCategory(name = CategoryTitle.FRIENDS_AND_LOVER, imageUrl = ""))

        val entertainment = TransactionCategory(name = CategoryTitle.ENTERTAINMENT, imageUrl = "")
        categories.add(entertainment)
        categories.add(TransactionCategory(name = CategoryTitle.MOVIES, imageUrl = "", parentCategory = entertainment))
        categories.add(TransactionCategory(name = CategoryTitle.GAMES, imageUrl = "", parentCategory = entertainment))

        categories.add(TransactionCategory(name = CategoryTitle.TRAVEL, imageUrl = ""))

        val health = TransactionCategory(name = CategoryTitle.HEALTH_FITNESS, imageUrl = "")
        categories.add(health)
        categories.add(TransactionCategory(name = CategoryTitle.SPORTS, imageUrl = "", parentCategory = health))
        categories.add(TransactionCategory(name = CategoryTitle.DOCTOR, imageUrl = "", parentCategory = health))
        categories.add(TransactionCategory(name = CategoryTitle.PHARMACY, imageUrl = "", parentCategory = health))
        categories.add(TransactionCategory(name = CategoryTitle.PERSONAL_CARE, imageUrl = "", parentCategory = health))

        val gift = TransactionCategory(name = CategoryTitle.GIFT_DONATIONS, imageUrl = "")
        categories.add(gift)
        categories.add(TransactionCategory(name = CategoryTitle.MARRIAGE, imageUrl = "", parentCategory = gift))
        categories.add(TransactionCategory(name = CategoryTitle.FUNERAL, imageUrl = "", parentCategory = gift))
        categories.add(TransactionCategory(name = CategoryTitle.CHARITY, imageUrl = "", parentCategory = gift))

        val family = TransactionCategory(name = CategoryTitle.FAMILY, imageUrl = "")
        categories.add(family)
        categories.add(TransactionCategory(name = CategoryTitle.CHILDREN_BABY, imageUrl = "", parentCategory = family))
        categories.add(TransactionCategory(name = CategoryTitle.HOME_IMPROVEMENT, imageUrl = "", parentCategory = family))
        categories.add(TransactionCategory(name = CategoryTitle.HOME_SERVICES, imageUrl = "", parentCategory = family))
        categories.add(TransactionCategory(name = CategoryTitle.HOME_SERVICES, imageUrl = "", parentCategory = family))
        categories.add(TransactionCategory(name = CategoryTitle.PETS, imageUrl = "", parentCategory = family))

        val education = TransactionCategory(name = CategoryTitle.EDUCATION, imageUrl = "")
        categories.add(education)
        categories.add(TransactionCategory(name = CategoryTitle.BOOKS, imageUrl = "", parentCategory = education))

        categories.add(TransactionCategory(name = CategoryTitle.INVESTMENT, imageUrl = ""))
        categories.add(TransactionCategory(name = CategoryTitle.BUSSINESSS, imageUrl = ""))
        categories.add(TransactionCategory(name = CategoryTitle.INSURANCES, imageUrl = ""))
        categories.add(TransactionCategory(name = CategoryTitle.FEES_CHARGES, imageUrl = ""))
        categories.add(TransactionCategory(name = CategoryTitle.WITHDRAWAL, imageUrl = ""))
        categories.add(TransactionCategory(name = CategoryTitle.OTHER, imageUrl = ""))

        categories.add(TransactionCategory(name = CategoryTitle.SALARY, imageUrl = "", type = CategoryType.INCOME))
        categories.add(TransactionCategory(name = CategoryTitle.AWARD, imageUrl = "", type = CategoryType.INCOME))
        categories.add(TransactionCategory(name = CategoryTitle.INTEREST_MONEY, imageUrl = "", type = CategoryType.INCOME))
        categories.add(TransactionCategory(name = CategoryTitle.PARENT, imageUrl = "", type = CategoryType.INCOME))
        categories.add(TransactionCategory(name = CategoryTitle.SELLING, imageUrl = "", type = CategoryType.INCOME))
        categories.add(TransactionCategory(name = CategoryTitle.GIFTS, imageUrl = "", type = CategoryType.INCOME))
        categories.add(TransactionCategory(name = CategoryTitle.OTHER, imageUrl = "", type = CategoryType.INCOME))


        categories.forEach { it.user = user }

        categoryRepo.saveAll(categories)
    }
}