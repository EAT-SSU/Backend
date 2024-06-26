package com.ssu.eatssu.domain.meal.application

import com.ssu.eatssu.domain.meal.persistence.MealQueryDslRepository
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsResponse
import com.ssu.eatssu.domain.meal.presentation.dto.MenusInMealResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MealQueryService(
    val mealQueryDslRepository: MealQueryDslRepository
) {
    @Cacheable(value = ["meals"], key = "#request.date.toString() + #request.restaurant.name() + #request.timePart.name()")
    fun getMeals(request: GetMealsRequest): GetMealsResponse {
        val meals = mealQueryDslRepository.find(
            date = request.date,
            restaurant = request.restaurant,
            timePart = request.timePart
        )

        return GetMealsResponse.from(meals!!)
    }

    fun getMenusInMeal(mealId: Long): MenusInMealResponse {
        val meal = mealQueryDslRepository.find(id = mealId)?.first()
        return MenusInMealResponse.from(meal!!)
    }
}