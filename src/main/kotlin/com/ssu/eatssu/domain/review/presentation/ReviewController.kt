package com.ssu.eatssu.domain.review.presentation

import com.ssu.eatssu.domain.review.application.ReviewCommandService
import com.ssu.eatssu.domain.review.application.ReviewQueryService
import com.ssu.eatssu.domain.review.application.ReviewSliceService
import com.ssu.eatssu.domain.review.presentation.dto.*
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/reviews")
class ReviewController(
    private val reviewCommandService: ReviewCommandService,
    private val reviewQueryService: ReviewQueryService,
    private val reviewSliceService: ReviewSliceService
) : ReviewControllerDocs {

    @GetMapping("/menus/reviews")
    override fun getMenuReviews(
        @RequestParam(name = "menuId")
        menuId: Long,
        lastReviewId: Long?,
        @ParameterObject @PageableDefault(
            size = 20,
            sort = ["date"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
        isDesc: Boolean?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): ResponseEntity<SliceResponse<ReviewDetailResponse>> {
        return ResponseEntity(
            reviewSliceService.getAllMenuReviews(
                menuId,
                pageable,
                lastReviewId,
                isDesc ?: true,
                customUserDetails
            ),
            HttpStatus.OK
        )
    }

    @GetMapping("/meals/reviews")
    override fun getMealReviews(
        @RequestParam(name = "mealId")
        mealId: Long,
        lastReviewId: Long?,
        @ParameterObject @PageableDefault(
            size = 20,
            sort = ["date"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
        isDesc: Boolean?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): ResponseEntity<SliceResponse<ReviewDetailResponse>> {
        return ResponseEntity(
            reviewSliceService.getAllMealReviews(
                mealId,
                pageable,
                lastReviewId,
                isDesc ?: true,
                customUserDetails
            ),
            HttpStatus.OK
        )
    }


    @GetMapping("/menus/reviews/summary")
    override fun getMainReviews(
        @RequestParam(name = "menuId") menuId: Long
    ): ResponseEntity<GetMenuReviewResponse> {
        return ResponseEntity(
            reviewQueryService.getMenuReview(menuId),
            HttpStatus.OK
        )
    }

    @GetMapping("/meals/reviews/summary")
    override fun getMealReviews(
        @RequestParam(name = "mealId") mealId: Long
    ): ResponseEntity<GetMealReviewResponse> {
        return ResponseEntity(
            reviewQueryService.getMealReview(mealId),
            HttpStatus.OK
        )
    }

    @PostMapping
    override fun createReview(
        @RequestPart request: CreateReviewRequest?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ): ResponseEntity<CreateReviewResponse> {
        return ResponseEntity(
            reviewCommandService.createReview(
                customUserDetails!!.getId(),
                request!!,
            ),
            HttpStatus.OK
        )
    }

    @PostMapping("/image")
    override fun uploadReviewImage(
        @RequestPart image: MultipartFile?): ResponseEntity<String> {
        return ResponseEntity(
            reviewCommandService.uploadImage(image!!),
            HttpStatus.OK
        )
    }

    @PatchMapping
    override fun updateReview(
        @RequestParam(name = "reviewId") reviewId: Long?,
        @RequestPart request: UpdateReviewRequest?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ): ResponseEntity<*> {
        return ResponseEntity(
            reviewCommandService.updateReview(
                customUserDetails!!.getId(),
                reviewId!!,
                request!!
            ),
            HttpStatus.OK
        )
    }

    @DeleteMapping
    override fun deleteReview(
        @RequestParam(name = "reviewId") reviewId: Long?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ): ResponseEntity<Void> {
        reviewCommandService.deleteReview(
            customUserDetails!!.getId(),
            reviewId!!
        )
        return ResponseEntity(HttpStatus.OK)
    }
}