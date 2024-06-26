package com.ssu.eatssu.domain.review.presentation.dto

import com.ssu.eatssu.domain.meal.entity.Meal

data class GetMealReviewResponse(
    val menuNames: List<String>,
    val totalReviewCount: Int,
    val averageMainRating: Double?,
    val averageAmountRating: Double?,
    val averageTasteRating: Double?,
    val ratingCountMap: Map<Int, Long?>
) {
    companion object {
        fun of(
            meal: Meal,
            averageMainRating: Double? = 0.0,
            averageAmountRating: Double? = 0.0,
            averageTasteRating: Double? = 0.0,
            ratingCountMap: Map<Int, Long?>
        ): GetMealReviewResponse {
            return GetMealReviewResponse(
                meal.menus.map { it.name },
                meal.menus.flatMap { it.reviews }.count(),
                averageMainRating,
                averageAmountRating,
                averageTasteRating,
                ratingCountMap
            )
        }
    }
}