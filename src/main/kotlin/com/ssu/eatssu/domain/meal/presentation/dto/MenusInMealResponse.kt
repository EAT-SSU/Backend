package com.ssu.eatssu.domain.meal.presentation.dto

import com.ssu.eatssu.domain.meal.entity.Meal

data class MenusInMealResponse(
    val menus: List<MenuIdNameResponse>
) {
    companion object {
        fun from(meal: Meal): MenusInMealResponse {
            return MenusInMealResponse(
                menus = meal.mealMenus.mapNotNull { MenuIdNameResponse.of(it.menu) }
            )
        }
    }
}



