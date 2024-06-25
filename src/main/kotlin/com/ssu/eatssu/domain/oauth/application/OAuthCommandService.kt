package com.ssu.eatssu.domain.oauth.application

import com.ssu.eatssu.domain.oauth.entity.OAuthProvider
import com.ssu.eatssu.domain.oauth.presentation.dto.AppleLoginRequest
import com.ssu.eatssu.domain.oauth.presentation.dto.OAuthLoginRequest
import com.ssu.eatssu.domain.user.application.UserCommandService
import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.global.auth.jwt.JwtTokenProvider
import com.ssu.eatssu.global.auth.jwt.Tokens
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OAuthCommandService(
    private val userCommandService: UserCommandService,
    private val userRepository: UserRepository,
    private val appleAuthenticator: AppleAuthenticator,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun kakaoLogin(request: OAuthLoginRequest): Tokens {
        val user = userRepository.findByProviderId(request.providerId)
            ?: userCommandService.createUser(request.email, OAuthProvider.KAKAO, request.providerId)

        return jwtTokenProvider.createTokens(request.email)
    }

    fun appleLogin(request: AppleLoginRequest): Tokens {
        val oAuthRequest = appleAuthenticator.toOAuthLoginRequest(request.identityToken)


        val user = userRepository.findByProviderId(oAuthRequest.providerId)
            ?: userCommandService.createUser(
                oAuthRequest.email,
                OAuthProvider.KAKAO,
                oAuthRequest.providerId
            )

        updateAppleEmail(user, oAuthRequest.email)

        return jwtTokenProvider.createTokens(oAuthRequest.email)
    }

    private fun updateAppleEmail(
        user: User,
        email: String
    ) {
        if (isHideEmail(user.email) && !isHideEmail(email))
            user.changeEmail(email)
    }

    private fun isHideEmail(email: String): Boolean {
        return email.contains("@privaterelay.appleid.com")
    }

    fun reissueTokens(email: String): Tokens {
        return jwtTokenProvider.createTokens(email)
    }
}