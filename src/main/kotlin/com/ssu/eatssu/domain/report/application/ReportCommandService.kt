package com.ssu.eatssu.domain.report.application

import com.ssu.eatssu.domain.report.entity.Report
import com.ssu.eatssu.domain.report.entity.ReportType
import com.ssu.eatssu.domain.report.persistence.ReportRepository
import com.ssu.eatssu.domain.report.presentation.dto.CreateReportRequest
import com.ssu.eatssu.domain.report.presentation.dto.CreateReportResponse
import com.ssu.eatssu.domain.review.persistence.ReviewRepository
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReportCommandService(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository,
    private val reportRepository: ReportRepository
) {

    fun reportReview(
        userDetails: CustomUserDetails,
        request: CreateReportRequest
    ): CreateReportResponse {
        val user = userRepository.findById(userDetails.getId()).get()
        val review = reviewRepository.findById(request.reviewId).get()

        val report = Report.of(user, review, request.reportType, request.content)
        return CreateReportResponse.of(reportRepository.save(report))
    }

    fun getReportType(): List<String> {
        return ReportType.values().map { it.name }
    }
}