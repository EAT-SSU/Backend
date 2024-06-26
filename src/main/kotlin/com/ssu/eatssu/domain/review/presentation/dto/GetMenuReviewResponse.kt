package com.ssu.eatssu.domain.review.presentation.dto

import com.ssu.eatssu.domain.menu.entity.Menu

data class GetMenuReviewResponse(
    val menuName: String,
    val totalReviewCount: Int,
    val averageMainRating: Double?,
    val averageAmountRating: Double?,
    val averageTasteRating: Double?,
    val ratingCountMap: Map<Int, Long?>
) {
    companion object {
        fun of(
            menu: Menu,
            averageMainRating: Double? = 0.0,
            averageAmountRating: Double? = 0.0,
            averageTasteRating: Double? = 0.0,
            ratingCountMap: Map<Int, Long?>
        ): GetMenuReviewResponse {
            return GetMenuReviewResponse(
                menu.name,
                menu.reviews.count(),
                averageMainRating,
                averageAmountRating,
                averageTasteRating,
                ratingCountMap
            )
        }
    }
}
