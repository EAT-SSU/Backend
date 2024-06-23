package com.ssu.eatssu.domain.restaurant.entity

import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException

enum class Restaurant(
    val restaurantName: String,
    val price: Int?) {
    DODAM("도담 식당", 6000),
    DORMITORY("기숙사 식당", 5500),
    FOOD_COURT("푸드 코트", null),
    SNACK_CORNER("스낵 코너", null),
    HAKSIK("학생 식당", 5000);

    companion object {
        fun from(restaurantName: String): Restaurant {
            return entries.firstOrNull { it.name.equals(restaurantName)}
                ?: throw NotExistsException(ErrorMessages.NOT_EXIST_RESTAURANT.message)
        }
    }
}