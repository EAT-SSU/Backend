package com.ssu.eatssu.domain.user.presentation

import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserEmailRequest
import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserNicknameRequest
import com.ssu.eatssu.domain.user.presentation.dto.UserMypageResponse
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "User", description = "User API")
interface UserControllerDocs {
    @Operation(
        summary = "이메일 중복 체크", description = """
        이메일 중복 체크 API 입니다.<br><br>
        중복되지 않은 이메일이면 true 를 반환합니다
        
        """
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "중복되지 않은 이메일")
        ]
    )
    @PostMapping("/verify-email")
    fun verifyDuplicatedEmail(
        @Parameter(description = "이메일") @RequestParam email: String
    ): ResponseEntity<Void>

    @Operation(
        summary = "닉네임 중복 체크", description = """
        닉네임 중복 체크 API 입니다.<br><br>
        """
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "중복되지 않은 닉네임")
        ]
    )
    @PostMapping("/validate-email")
    fun verifyDuplicatedNickname(
        @Parameter(description = "닉네임") @RequestParam nickname: String
    ): ResponseEntity<Void>

    @Operation(
        summary = "닉네임 변경", description = """
        닉네임 변경 API 입니다.<br><br>
        """
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "중복되지 않은 닉네임")
        ]
    )
    @PostMapping("/change-nickname")
    fun changeNickname(
        @Parameter(description = "닉네임") @RequestBody request: ChangeUserNicknameRequest,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<Void>

    @Operation(
        summary = "이메일 변경", description = """
        닉네임 변경 API 입니다.<br><br>
        """
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "중복되지 않은 이메일")
        ]
    )
    @PostMapping("/change-email")
    fun changeEmail(
        @Parameter(description = "이메일") @RequestBody request: ChangeUserEmailRequest,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<Void>

    @Operation(
        summary = "유저 삭제", description = """
        유저 삭제 API 입니다.<br><br>
        """
    )
    @DeleteMapping
    fun deleteUser(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<Void>

    @Operation(
        summary = "마이페이지 조회", description = """
        마이 페이지 조회 API 입니다.<br><br>
        """
    )
    @GetMapping("/mypage")
    fun getMyPage(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<UserMypageResponse>
}