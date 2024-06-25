package com.ssu.eatssu.domain.oauth.presentation

import com.ssu.eatssu.domain.oauth.application.OAuthCommandService
import com.ssu.eatssu.domain.oauth.presentation.dto.AppleLoginRequest
import com.ssu.eatssu.domain.oauth.presentation.dto.OAuthLoginRequest
import com.ssu.eatssu.global.auth.jwt.Tokens
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/oauth")
class OAuthController(
    private val oAuthCommandService: OAuthCommandService
) : OAuthControllerDocs {
    override fun kakaoLogin(request: OAuthLoginRequest): ResponseEntity<Tokens> {
        return ResponseEntity(oAuthCommandService.kakaoLogin(request), HttpStatus.OK)
    }

    override fun appleLogin(request: AppleLoginRequest): ResponseEntity<Tokens> {
        return ResponseEntity(oAuthCommandService.appleLogin(request), HttpStatus.OK)
    }

    override fun reissueToken(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<Tokens> {
        return ResponseEntity(
            oAuthCommandService.reissueTokens(userDetails.username),
            HttpStatus.OK
        )
    }

}