package com.ssu.eatssu.domain.inquiry.application

import com.ssu.eatssu.domain.inquiry.entity.Inquiry
import com.ssu.eatssu.domain.inquiry.persistence.InquiryRepository
import com.ssu.eatssu.domain.inquiry.presentation.dto.CreateInquiryRequest
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InquiryCommandService(
    private val userRepository: UserRepository,
    private val inquiryRepository: InquiryRepository
) {
    fun createInquiry(
        userDetails: CustomUserDetails,
        request: CreateInquiryRequest
    ): Inquiry {
        val user = userRepository.findById(userDetails.getId()).orElse(
            throw NotExistsException(ErrorMessages.NOT_EXIST_USER.message)
        )

        val inquiry = Inquiry.of(user, request.title, request.content, request.email)
        return inquiryRepository.save(inquiry)
    }
}