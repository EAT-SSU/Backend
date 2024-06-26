package com.ssu.eatssu.domain.inquiry.presentation.dto

data class CreateInquiryRequest(
    val title: String,
    val content: String,
    val email: String
)
