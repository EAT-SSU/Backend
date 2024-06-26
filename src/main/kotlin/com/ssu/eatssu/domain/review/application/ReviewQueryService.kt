package com.ssu.eatssu.domain.review.application

import com.ssu.eatssu.domain.meal.persistence.MealRepository
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.review.persistence.ReviewQueryDslRepository
import com.ssu.eatssu.domain.review.presentation.dto.GetMealReviewResponse
import com.ssu.eatssu.domain.review.presentation.dto.GetMenuReviewResponse
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReviewQueryService(
    private val menuRepository: MenuRepository,
    private val mealRepository: MealRepository,
    private val reviewQueryDslRepository: ReviewQueryDslRepository
) {
    fun getMenuReview(menuId: Long): GetMenuReviewResponse {
        val menu = menuRepository.findById(menuId).orElse(
            throw NotExistsException(ErrorMessages.NOT_EXISTS_MENU.message)
        )

        val averageMainRating = reviewQueryDslRepository.findAverageMainRatingByMenu(menuId)
        val averageAmountRating = reviewQueryDslRepository.findAverageAmountRatingByMenu(menuId)
        val averageTasteRating = reviewQueryDslRepository.findAverageTasteRatingByMenu(menuId)
        val ratingCountMap = reviewQueryDslRepository.findRatingCountMapByMenu(menuId)

        return GetMenuReviewResponse.of(
            menu,
            averageMainRating,
            averageAmountRating,
            averageTasteRating,
            ratingCountMap
        )
    }

    fun getMealReview(mealId: Long): GetMealReviewResponse {
        val meal = mealRepository.findById(mealId).orElse(
            throw NotExistsException(ErrorMessages.NOT_EXISTS_MEAL.message)
        )

        val averageMainRating = reviewQueryDslRepository.findAverageMainRatingByMeal(mealId)
        val averageAmountRating = reviewQueryDslRepository.findAverageAmountRatingByMeal(mealId)
        val averageTasteRating = reviewQueryDslRepository.findAverageTasteRatingByMeal(mealId)
        val ratingCountMap = reviewQueryDslRepository.findRatingCountMapByMeal(mealId)

        return GetMealReviewResponse.of(
            meal,
            averageMainRating,
            averageAmountRating,
            averageTasteRating,
            ratingCountMap
        )
    }

}