package com.ssu.eatssu.domain.meal.application

import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.meal.persistence.MealRepository
import com.ssu.eatssu.domain.meal.presentation.dto.CreateMealRequest
import com.ssu.eatssu.domain.menu.application.MenuQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MealCommandService(
    private val menuQueryService: MenuQueryService,
    private val mealRepository: MealRepository
) {

    fun createMeal(request: CreateMealRequest) : Meal {
        val meal = Meal.of(
            date = request.date,
            restaurant = request.restaurant,
            timePart = request.timePart,
            menus = request.menus.stream().map {
                menuQueryService.getMenu(it)
            }.toList()
        )

        return mealRepository.save(meal)
    }
}