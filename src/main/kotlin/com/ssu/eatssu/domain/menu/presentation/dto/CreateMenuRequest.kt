package com.ssu.eatssu.domain.menu.presentation.dto

data class CreateMenuRequest (
    val name: String,
    val price: Int,
    val restaurantName: String,
    val categoryName: String = "SNACK",
    val menuType: String = "ITEM",
    val menuStatus: String = "AVAILABLE",
)