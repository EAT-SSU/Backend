package com.ssu.eatssu.domain.review.presentation.dto

data class UpdateReviewRequest(
    val content: String,
    val mainRating: Int,
    val amountRating: Int,
    val tasteRating: Int,
)
