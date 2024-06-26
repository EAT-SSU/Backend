package com.ssu.eatssu.domain.review.presentation.dto

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.review.entity.Review
import com.ssu.eatssu.domain.user.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

data class SliceResponse<T>(
    val hasNext: Boolean,
    val numberOfElements: Int,
    val result: List<T>
) {
    companion object {
        fun <T> of(
            hasNext: Boolean,
            numberOfElements: Int,
            result: List<T>
        ): SliceResponse<T> {
            return SliceResponse(
                hasNext,
                numberOfElements,
                result
            )
        }
    }

}

data class MyReviewDetailResponse(
    val reviewId: Long,
    val menuName: String,
    val content: String,
    val mainRating: Double,
    val amountRating: Double,
    val tasteRating: Double,
    val imageUrls: List<String>,
    val createdAt: LocalDateTime
) {
    companion object {
        fun of(
            review: Review
        ): MyReviewDetailResponse {
            return MyReviewDetailResponse(
                review.id,
                review.menu.name,
                review.content,
                review.mainRating.toDouble(),
                review.amountRating.toDouble(),
                review.tasteRating.toDouble(),
                review.imageUrls,
                review.createdAt
            )
        }
    }
}

data class ReviewDetailResponse(
    val reviewId: Long,
    val menuName: String,
    val writerId: Long?,
    val writerNickname: String?,
    val isWriter: Boolean,
    val content: String,
    val mainRating: Double,
    val amountRating: Double,
    val tasteRating: Double,
    val imageUrls: List<String>,
    val createdAt: LocalDateTime
) {
    companion object {
        fun of(
            writer: User,
            isWriter: Boolean,
            menu: Menu,
            review: Review
        ): ReviewDetailResponse {
            return ReviewDetailResponse(
                review.id,
                menu.name,
                writer.id,
                writer.nickname,
                isWriter,
                review.content,
                review.mainRating.toDouble(),
                review.amountRating.toDouble(),
                review.tasteRating.toDouble(),
                review.imageUrls,
                review.createdAt
            )
        }
    }
}