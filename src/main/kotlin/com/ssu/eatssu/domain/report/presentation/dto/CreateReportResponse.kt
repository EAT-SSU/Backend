package com.ssu.eatssu.domain.report.presentation.dto

import com.ssu.eatssu.domain.report.entity.Report

data class CreateReportResponse(
    val id: Long,
    val reviewId: Long,
    val reportType: String,
    val content: String,
    val status: String
) {
    companion object {
        fun of(
            report: Report
        ): CreateReportResponse {
            return CreateReportResponse(
                report.id!!,
                report.review.id!!,
                report.reportType.name,
                report.content,
                report.reportStatus.name
            )
        }
    }
}
