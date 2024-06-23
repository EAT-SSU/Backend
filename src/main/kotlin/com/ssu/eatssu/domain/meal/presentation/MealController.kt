package com.ssu.eatssu.domain.meal.presentation

import com.ssu.eatssu.domain.meal.application.MealCommandService
import com.ssu.eatssu.domain.meal.application.MealQueryService
import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.meal.presentation.dto.CreateMealRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsResponse
import com.ssu.eatssu.domain.meal.presentation.dto.MenusInMealResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/meals")
class MealController(
    private val mealCommandService: MealCommandService,
    private val mealQueryService: MealQueryService
) : MealControllerDocs {

    @GetMapping
    override fun getMeals(@RequestBody getMealsRequest: GetMealsRequest): ResponseEntity<GetMealsResponse> {
        return ResponseEntity(
            mealQueryService.getMeals(getMealsRequest),
            HttpStatus.OK
        )
    }

    @PostMapping
    override fun createMeal(@RequestBody createMealRequest: CreateMealRequest): ResponseEntity<Meal> {
        return ResponseEntity(
            mealCommandService.createMeal(createMealRequest),
            HttpStatus.OK
        )
    }

    @GetMapping("/menus")
    override fun getMenusInMeal(@RequestParam(name = "mealId") mealId: Long): ResponseEntity<MenusInMealResponse> {
        return ResponseEntity(
            mealQueryService.getMenusInMeal(mealId),
            HttpStatus.OK
        )
    }

    @DeleteMapping
    override fun deleteMeal(@RequestParam(name = "mealId") mealId: Long): ResponseEntity<Void> {
        mealCommandService.deleteMeal(mealId)
        return ResponseEntity(HttpStatus.OK)
    }
}