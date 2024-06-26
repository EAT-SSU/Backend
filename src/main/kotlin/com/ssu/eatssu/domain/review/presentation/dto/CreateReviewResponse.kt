package com.ssu.eatssu.domain.review.presentation.dto

import com.ssu.eatssu.domain.review.entity.Review

data class CreateReviewResponse(
    val reviewId: Long,
    val content: String,
    val imageUrl: List<String>,
    val mainRating: Int,
    val amountRating: Int,
    val tasteRating: Int,
    val menuId: Long,
    val userId: Long
) {
    companion object {
        fun of(review: Review) : CreateReviewResponse {
            return CreateReviewResponse(
                reviewId = review.id,
                content = review.content,
                imageUrl = review.imageUrls,
                mainRating = review.mainRating,
                amountRating = review.amountRating,
                tasteRating = review.tasteRating,
                menuId = review.menu.id!!,
                userId = review.user.id!!
            )
        }
    }
}
