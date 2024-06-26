package com.ssu.eatssu.domain.review.application

import com.ssu.eatssu.domain.meal.persistence.MealRepository
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.review.persistence.ReviewQueryDslRepository
import com.ssu.eatssu.domain.review.presentation.dto.MyReviewDetailResponse
import com.ssu.eatssu.domain.review.presentation.dto.ReviewDetailResponse
import com.ssu.eatssu.domain.review.presentation.dto.SliceResponse
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewSliceService(
    private val reviewQueryDslRepository: ReviewQueryDslRepository,
    private val menuRepository: MenuRepository,
    private val mealRepository: MealRepository,
    private val userRepository: UserRepository
) {

    fun getAllMenuReviews(
        menuId: Long,
        pageable: Pageable,
        lastReviewId: Long?,
        isDesc: Boolean = true,
        customUserDetails: CustomUserDetails
    ) : SliceResponse<ReviewDetailResponse>{
        val menu = menuRepository.findById(menuId).orElseThrow {
            throw NotExistsException(ErrorMessages.NOT_EXISTS_MENU.message)
        }

        val reviews =
            reviewQueryDslRepository.findAllByMenuId(menuId, pageable, lastReviewId, isDesc)

        return SliceResponse.of(reviews.hasNext(),
            reviews.count(),
            reviews.content.map {
                val writer = userRepository.findById(it.user.id!!).get()
                val isWriter = writer.id == customUserDetails.getId()
                val menu = menuRepository.findById(it.menu.id!!).get()
                val review = it
                ReviewDetailResponse.of(writer, isWriter, menu, review)
            })
    }

    fun getAllMealReviews(
        mealId: Long,
        pageable: Pageable,
        lastReviewId: Long?,
        isDesc: Boolean = true,
        customUserDetails: CustomUserDetails
    ) : SliceResponse<ReviewDetailResponse> {
        val meal = mealRepository.findById(mealId).orElseThrow {
            throw NotExistsException(ErrorMessages.NOT_EXISTS_MEAL.message)
        }

        val reviews =
            reviewQueryDslRepository.findAllByMealId(meal, pageable, lastReviewId, isDesc)

        return SliceResponse.of(reviews.hasNext(),
            reviews.count(),
            reviews.content.map {
                val writer = userRepository.findById(it.user.id!!).get()
                val isWriter = writer.id == customUserDetails.getId()
                val menu = menuRepository.findById(it.menu.id!!).get()
                val review = it
                ReviewDetailResponse.of(writer, isWriter, menu, review)
            })
    }

    fun getAllMyReviews(
        pageable: Pageable,
        lastReviewId: Long?,
        isDesc: Boolean = true,
        customUserDetails: CustomUserDetails
    ) : SliceResponse<MyReviewDetailResponse>{
        val reviews =
            reviewQueryDslRepository.findAllByUserId(
                customUserDetails.getId(),
                pageable,
                lastReviewId,
                isDesc
            )

        return SliceResponse.of(reviews.hasNext(),
            reviews.count(),
            reviews.content.map {
                val menu = menuRepository.findById(it.menu.id!!).get()
                MyReviewDetailResponse.of(it)
            })
    }
}