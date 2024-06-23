package com.ssu.eatssu.domain.meal.application

import com.ssu.eatssu.domain.meal.persistence.MealQueryDslRepository
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MealQueryService(
    val mealQueryDslRepository: MealQueryDslRepository
) {
    fun getMeals(request: GetMealRequest): GetMealsResponse {
        val meals = mealQueryDslRepository.find(
            date = request.date,
            restaurant = request.restaurant,
            timePart = request.timePart
        )

        return GetMealsResponse.from(meals!!)
    }
}