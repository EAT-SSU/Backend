package com.ssu.eatssu.domain.inquiry.presentation

import com.ssu.eatssu.domain.inquiry.entity.Inquiry
import com.ssu.eatssu.domain.inquiry.presentation.dto.CreateInquiryRequest
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Inquiry", description = "문의 관련 API")
interface InquiryControllerDocs {
    @Operation(summary = "문의 작성", description = "문의를 작성하는 API 입니다.")
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "문의 작성 성공"), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 유저",
        )]
    )
    @PostMapping
    fun writeInquiry(
        @RequestBody createInquiryRequest: CreateInquiryRequest?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ): ResponseEntity<Inquiry>
}