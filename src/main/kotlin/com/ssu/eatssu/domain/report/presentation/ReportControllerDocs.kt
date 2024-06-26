package com.ssu.eatssu.domain.report.presentation

import com.ssu.eatssu.domain.report.entity.ReportType
import com.ssu.eatssu.domain.report.presentation.dto.CreateReportRequest
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Report", description = "리뷰 신고 API")
interface ReportControllerDocs {
    @Operation(summary = "리뷰 신고 사유 종류 조회", description = "리뷰 신고 사유 종류를 조회하는 API 입니다.")
    @GetMapping("/types")
    fun getReportType(): ResponseEntity<List<ReportType>>


    @Operation(summary = "리뷰 신고하기", description = "리뷰를 신고하는 API 입니다.")
    @PostMapping("")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 신고 성공",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 유저",
        )]
    )
    fun reportReview(
        @RequestBody createReportRequest: CreateReportRequest?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ): ResponseEntity<Void>
}