package com.ssu.eatssu.domain.menu.entity

import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException

enum class MenuCategory(
    val description: String,
) {

    SALAD("샐러드"),
    FUSION("퓨전"),
    SOUP("국밥"),
    SNACK("분식"),
    RAMEN("라면"),
    SET("세트"),
    EXTRA("기타"),
    DRINK("음료"),
    RICE("밥"),
    CHICKEN("치킨");

    companion object {
        fun from(categoryName: String): MenuCategory {
            return entries.firstOrNull { it.name.equals(categoryName) }
                ?: throw NotExistsException(ErrorMessages.NOT_EXIST_CATEGORY.message)
        }
    }
}