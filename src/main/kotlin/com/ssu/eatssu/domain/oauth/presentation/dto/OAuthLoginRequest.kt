package com.ssu.eatssu.domain.oauth.presentation.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class OAuthLoginRequest(
    @NotBlank
    @Email
    val email: String,

    @NotBlank
    val providerId: String
)
