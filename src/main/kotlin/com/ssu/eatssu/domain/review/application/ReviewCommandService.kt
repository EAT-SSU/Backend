package com.ssu.eatssu.domain.review.application

import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.review.entity.Review
import com.ssu.eatssu.domain.review.persistence.ReviewRepository
import com.ssu.eatssu.domain.review.presentation.dto.CreateReviewRequest
import com.ssu.eatssu.domain.review.presentation.dto.CreateReviewResponse
import com.ssu.eatssu.domain.review.presentation.dto.UpdateReviewRequest
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class ReviewCommandService(
    private val reviewRepository: ReviewRepository,
    private val reviewImageProcessor: ReviewImageProcessor,
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository
) {
    fun createReview(
        userDetails: CustomUserDetails,
        request: CreateReviewRequest
    ): CreateReviewResponse {
        val user = userRepository.findById(userDetails.getId()).get()
        val menu = menuRepository.findById(request.menuId).get()
        val review = Review.of(
            user = user,
            menu = menu,
            content = request.content,
            mainRating = request.mainRating,
            amountRating = request.amountRating,
            tasteRating = request.tasteRating
        )

        uplodeImages(review, request.images)
        user.addReview(review)
        menu.addReview(review)

        return CreateReviewResponse.of(review)
    }

    fun uploadImage(image: MultipartFile): String {
        return reviewImageProcessor.processImage(image)
    }

    private fun uplodeImages(review: Review, images: List<MultipartFile>) {
        for (image in images) {
            val imageUrl = reviewImageProcessor.processImage(image)
            review.addImageUrl(imageUrl)
        }
    }

    fun updateReview(
        userDetails: CustomUserDetails,
        reviewId: Long,
        request: UpdateReviewRequest
    ) {
        val user = userRepository.findById(userDetails.getId()).get()
        val review = reviewRepository.findById(reviewId).get()

        review.isWrittenBy(user)
            .apply {
                review.update(
                    content = request.content,
                    mainRating = request.mainRating,
                    amountRating = request.amountRating,
                    tasteRating = request.tasteRating
                )
            }
    }

    fun deleteReview(
        userDetails: CustomUserDetails,
        reviewId: Long
    ) {
        val user = userRepository.findById(userDetails.getId()).get()
        val review = reviewRepository.findById(reviewId).get()

        review.isWrittenBy(user)
            .apply {
                reviewRepository.delete(review)
            }
    }
}