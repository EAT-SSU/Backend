package com.ssu.eatssu.domain.oauth.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

data class AppleLoginRequest(
    @Schema(description = "Apple Identity Token", example = "eyJraWQiOiJXNldjT0tCIiwiYWxnIjoi")
    val identityToken: String
)
