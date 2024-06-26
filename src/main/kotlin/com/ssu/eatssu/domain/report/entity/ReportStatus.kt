package com.ssu.eatssu.domain.report.entity

enum class ReportStatus(
    val description: String
) {
    PENDING("대기 중"),
    IN_PROGRESS("진행 중"),
    RESOLVED("해결됨"),
    REJECTED("거절됨"),
    FALSE_REPORT("거짓 신고");
}