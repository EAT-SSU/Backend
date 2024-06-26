package com.ssu.eatssu.domain.report.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateReportRequest(
    @Schema(description = "신고 할 리뷰 식별자", example = "1")
    val reviewId: Long,
    @Schema(description = "신고 유형", example = "COPY")
    val reportType: String,
    @Schema(description = "신고 내용", example = "이 리뷰는 다른 리뷰를 복사한 것 같아요.")
    val content: String
)
