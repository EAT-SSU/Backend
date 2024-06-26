package com.ssu.eatssu.domain.review.presentation

import com.ssu.eatssu.domain.menu.entity.MenuType
import com.ssu.eatssu.domain.review.presentation.dto.*
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Review", description = "리뷰 API")
interface ReviewControllerDocs {

    @Operation(
        summary = "리뷰 리스트 조회", description = """
            리뷰 리스트를 조회하는 API 입니다.<br><br>
            menuType=FIX 의 경우 menuId 파라미터를 넣어주세요.<br><br>
            menuType=CHANGE 의 경우 mealId 파라미터를 넣어주세요.<br><br>
            커서 기반 페이지네이션으로 리뷰 리스트를 조회합니다.<br><br>
            페이징 기본 값 = {size=20, sort=date, direction=desc}<br><br>
            """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 리스트 조회 성공"
        ), ApiResponse(
            responseCode = "400",
            description = "쿼리 파라미터 누락",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 메뉴",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 식단",
        )]
    )
    fun getMenuReviews(
        @Parameter(description = "menuId(고정메뉴)") @RequestParam(
            value = "menuId",
            required = false
        ) menuId: Long,
        @Parameter(
            description = "마지막으로 조회된 reviewId값(첫 조회시 값 필요 없음)",
            `in` = ParameterIn.QUERY
        ) @RequestParam(value = "lastReviewId", required = false) lastReviewId: Long?,
        @ParameterObject @PageableDefault(
            size = 20,
            sort = ["date"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
        isDesc: Boolean?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): ResponseEntity<SliceResponse<ReviewDetailResponse>>

    @Operation(
        summary = "리뷰 리스트 조회", description = """
            리뷰 리스트를 조회하는 API 입니다.<br><br>
            menuType=FIX 의 경우 menuId 파라미터를 넣어주세요.<br><br>
            menuType=CHANGE 의 경우 mealId 파라미터를 넣어주세요.<br><br>
            커서 기반 페이지네이션으로 리뷰 리스트를 조회합니다.<br><br>
            페이징 기본 값 = {size=20, sort=date, direction=desc}<br><br>
            """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 리스트 조회 성공"
        ), ApiResponse(
            responseCode = "400",
            description = "쿼리 파라미터 누락",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 메뉴",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 식단",
        )]
    )
    fun getMealReviews(
        @Parameter(description = "mealId(변동메뉴)") @RequestParam(
            value = "mealId",
            required = false
        ) mealId: Long,
        @Parameter(
            description = "마지막으로 조회된 reviewId값(첫 조회시 값 필요 없음)",
            `in` = ParameterIn.QUERY
        ) @RequestParam(value = "lastReviewId", required = false) lastReviewId: Long?,
        @ParameterObject @PageableDefault(
            size = 20,
            sort = ["date"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
        isDesc: Boolean?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): ResponseEntity<SliceResponse<ReviewDetailResponse>>


    @Operation(
        summary = "리뷰 작성", description = """
            리뷰를 작성하는 API 입니다.<br><br>
            reviewCreate는 application/json, multipartFileList는 multipart/form-data로 요청해주세요.<br><br>
            사진은 여러장 첨부 가능합니다.(기획상으로는 한 장만 첨부하도록 제한이 있지만 API 스펙 자체는 여러 장 첨부 가능)
            
            """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 작성 성공"
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 메뉴",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 유저",
        ), ApiResponse(
            responseCode = "500",
            description = "이미지 업로드 실패",
        )]
    )
    fun createReview(
        @RequestPart request: CreateReviewRequest?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ) : ResponseEntity<CreateReviewResponse>


    @Operation(summary = "리뷰 이미지 업로드", description = "리뷰 이미지를 업로드하고 이미지 URL 을 반환합니다.")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "이미지 업로드 및 URL 반환 성공"
        ), ApiResponse(
            responseCode = "500",
            description = "이미지 업로드 실패",
        )]
    )
    fun uploadReviewImage(@RequestPart(value = "image") image: MultipartFile?): ResponseEntity<String>

    // Todo : 반환된 S3 이미지를 DTO 클래스에 넣어서 리뷰 작성하는 API 추가

    @Operation(
        summary = "리뷰 수정(글 수정)", description = """
            리뷰 내용을 수정하는 API 입니다.<br><br>
            글 수정만 가능하며 사진 수정은 지원하지 않습니다.
            
            """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 수정 성공"
        ), ApiResponse(
            responseCode = "403",
            description = "리뷰에 대한 권한이 없음",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 리뷰",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 유저",
        )]
    )
    @PatchMapping("/{reviewId}")
    fun updateReview(
        @Parameter(description = "reviewId") @PathVariable("reviewId") reviewId: Long?,
        @RequestBody request: UpdateReviewRequest?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ): ResponseEntity<*>

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제하는 API 입니다.")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 삭제 성공"
        ), ApiResponse(
            responseCode = "403",
            description = "리뷰에 대한 권한이 없음",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 리뷰",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 유저",
        )]
    )
    fun deleteReview(
        @Parameter(description = "reviewId") @RequestParam("reviewId") reviewId: Long?,
        @AuthenticationPrincipal customUserDetails: CustomUserDetails?
    ): ResponseEntity<Void>

    @Operation(
        summary = "식단(변동 메뉴) 리뷰 정보 조회(메뉴명, 평점 등등)", description = """
            식단 리뷰 정보를 조회하는 API 입니다.<br><br>
            메뉴명 리스트, 리뷰 수, 메인 평점, 양 평점, 맛 평점, 각 평점의 개수를 조회합니다.
            
            """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 정보 조회 성공"
        ), ApiResponse(
            responseCode = "400",
            description = "쿼리 파라미터 누락",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 식단",
        )]
    )
    fun getMealReviews(
        @Parameter(description = "mealId") @RequestParam(value = "mealId") mealId: Long
    ): ResponseEntity<GetMealReviewResponse>

    @Operation(
        summary = "고정 메뉴 리뷰 정보 조회(메뉴명, 평점 등등)", description = """
            고정 메뉴 리뷰 정보를 조회하는 API 입니다.<br><br>
            메뉴명, 리뷰 수, 메인 평점, 양 평점, 맛 평점, 각 평점의 개수를 조회합니다.
            
            """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "리뷰 정보 조회 성공"
        ), ApiResponse(
            responseCode = "400",
            description = "쿼리 파라미터 누락",
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 메뉴",
        )]
    )
    fun getMainReviews(
        @Parameter(description = "menuId") @RequestParam(value = "menuId") menuId: Long
    ): ResponseEntity<GetMenuReviewResponse>
}