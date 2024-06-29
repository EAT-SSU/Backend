package com.ssu.eatssu.domain.meal.presentation.dto

import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.menu.entity.Menu

data class GetMealsResponse(
    val meals: List<MealDetailResponse>
) {
    companion object {
        fun from(meals: List<Meal>): GetMealsResponse {
            return GetMealsResponse(
                meals = meals.map(MealDetailResponse::of)
            )
        }
    }
}

data class MealDetailResponse(
    val mealId: Long,
    val price: Int,
    val rating: Double? = 0.0,
    val menus: List<MenuIdNameResponse>
) {
    companion object {
        fun of(meal: Meal): MealDetailResponse {
            return MealDetailResponse(
                mealId = meal.id!!,
                price = meal.price!!,
                rating = meal.rating,
                menus = meal.mealMenus.map { MenuIdNameResponse.of(it.menu) }
            )
        }
    }
}

data class MenuIdNameResponse(
    val menuId: Long,
    val menuName: String
) {
    companion object {
        fun of(menu: Menu): MenuIdNameResponse {
            return MenuIdNameResponse(menu.id!!, menu.name)
        }
    }
}