package com.mm_backend.constants

val MSG_USER_EXIST = "User already exist"
val MSG_USER_NOT_EXIST = "User not exist"
val MSG_INCORRECT_AUTH_INFO = "Incorrect Username / password"
val MSG_USER_DOES_NOT_OWN_THIS_WALLET = "User doesn't own this wallet"
val MSG_INVALID_TOKEN = "Invalid token"
val MSG_CATEGORY_INVALID = "Invalid category"
val MSG_INVALID_TRANSACTION_TYPE = "Invalid transaction type"

class Constants {
    companion object{
        val TYPE_INCOME="Income"
        val TYPE_EXPENSE="Expense"
    }
}

class CategoryTitle{
    companion object {
        val FOOD_AND_BEVERAGE = "Food & Beverage"
        val RESTAURANTS = "Restaurants"
        val CAFE = "Café"

        val BILL_AND_UTILITIES = "Bill & Utilities"
        val PHONE = "Phone"
        val WATER = "Water"
        val ELECTRICITY = "Electricity"
        val GAS = "Gas"
        val TELEVISION = "Television"
        val INTERNET = "Internet"
        val RENTALS = "Rentals"

        val TRANSPORTATION = "Transportation"
        val TAXI = "Taxi"
        val PARKING_FEES = "Parking Fees"
        val PETROL = "Petrol"
        val MAINTENANCE = "Maintenance"

        val SHOPPING = "Shopping"
        val CLOTHING = "Clothing"
        val FOOTWEAR = "Footwear"
        val ACCESSORIES = "Accessories"
        val ELECTRONICS = "Electronics"

        val FRIENDS_AND_LOVER = "Friends & Lover"
        val ENTERTAINMENT = "Entertainment"
        val MOVIES = "Movies"
        val GAMES = "Games"

        val TRAVEL = "Travel"

        val HEALTH_FITNESS = "HEALTH & FITNESS"
        val SPORTS = "Sports"
        val DOCTOR = "Doctor"
        val PHARMACY = "Pharmacy"
        val PERSONAL_CARE = "Personal Care"

        val GIFT_DONATIONS = "Gift & Donations"
        val MARRIAGE = "Marriage"
        val FUNERAL = "Funeral"
        val CHARITY = "Charity"

        val FAMILY = "Family"
        val CHILDREN_BABY = "Children & Baby"
        val HOME_IMPROVEMENT = "Home Improvement"
        val HOME_SERVICES = "Home Services"
        val PETS = "Pets"

        val EDUCATION = "Education"
        val BOOKS = "Books"

        val INVESTMENT = "Investment"
        val BUSSINESSS = "Bussiness"
        val INSURANCES = "Insurances"
        val FEES_CHARGES = "Fees & Charges"
        val WITHDRAWAL = "Withdrawal"
        val OTHER = "Other"

        val AWARD = "Award"
        val INTEREST_MONEY = "Interest Money"
        val SALARY = "Salary"
        val GIFTS = "Gift"
        val SELLING = "Selling"
        val PARENT = "Parent"
    }
}