package com.ssu.eatssu.domain.inquiry.presentation

import com.ssu.eatssu.domain.inquiry.application.InquiryCommandService
import com.ssu.eatssu.domain.inquiry.entity.Inquiry
import com.ssu.eatssu.domain.inquiry.presentation.dto.CreateInquiryRequest
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class InquiryController(
    private val inquiryCommandService: InquiryCommandService
) : InquiryControllerDocs {
    override fun writeInquiry(
        createInquiryRequest: CreateInquiryRequest?,
        customUserDetails: CustomUserDetails?
    ): ResponseEntity<Inquiry> {
        return ResponseEntity(
            inquiryCommandService.createInquiry(
                customUserDetails!!,
                createInquiryRequest!!
            ), HttpStatus.OK
        )
    }
}