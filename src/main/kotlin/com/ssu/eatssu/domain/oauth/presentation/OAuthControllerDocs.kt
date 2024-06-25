package com.ssu.eatssu.domain.oauth.presentation

import com.ssu.eatssu.domain.oauth.presentation.dto.AppleLoginRequest
import com.ssu.eatssu.domain.oauth.presentation.dto.OAuthLoginRequest
import com.ssu.eatssu.global.auth.jwt.Tokens
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "OAuth", description = "OAuth 관련 API")
interface OAuthControllerDocs {
    @Operation(
        summary = "카카오 회원가입, 로그인", description = """
        카카오 회원가입, 로그인 API 입니다.<br><br>
        가입된 회원일 경우 카카오 로그인, 미가입 회원일 경우 회원가입 후 자동 로그인됩니다.
        
        """
    )
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "카카오 회원가입/로그인 성공")])
    @PostMapping("/kakao")
    fun kakaoLogin(@RequestBody request: OAuthLoginRequest): ResponseEntity<Tokens>

    @Operation(
        summary = "애플 회원가입, 로그인", description = """
        애플 로그인, 회원가입 API 입니다.<br><br>
        가입된 회원일 경우 카카오 로그인, 미가입 회원일 경우 회원가입 후 자동 로그인됩니다.
        
        """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "애플 회원가입/로그인 성공"
        ), ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 유저",
        )]
    )
    @PostMapping("/apple")
    fun appleLogin(@RequestBody request: @Valid AppleLoginRequest): ResponseEntity<Tokens>

    @Operation(summary = "토큰 재발급", description = "accessToken, refreshToken 재발급 API 입니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "토큰 재발급 성공")])
    @PostMapping("/reissue/token")
    fun reissueToken(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<Tokens>
}