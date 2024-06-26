package com.ssu.eatssu.domain.review.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import org.springframework.web.multipart.MultipartFile

data class CreateReviewRequest(
    @Max(150)
    @Schema(description = "리뷰 내용", example = "맛있어요!")
    val content: String,
    @Schema(description = "메인 평점", example = "5")
    val mainRating: Int,
    @Schema(description = "양 평점", example = "5")
    val amountRating: Int,
    @Schema(description = "맛 평점", example = "5")
    val tasteRating: Int,
    @Schema(description = "메뉴 아이디", example = "1L")
    val menuId: Long,
    val images: List<MultipartFile>
)
