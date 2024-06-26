package com.ssu.eatssu.domain.slack.application

import com.ssu.eatssu.domain.inquiry.entity.Inquiry
import com.ssu.eatssu.domain.report.entity.Report
import com.ssu.eatssu.domain.review.entity.Review
import com.ssu.eatssu.domain.user.entity.User
import java.text.MessageFormat

class SlackMessageFormatter {
    fun sendReport(report: Report): String {
        val reporter: User = report.user
        val review: Review = report.review

        val messageFormat = MessageFormat(
            """
                        ===================
                        *신고자 INFO*
                        - 신고자 ID: {0}
                        - 닉네임: {1}
                        *신고된 리뷰 INFO*
                        - 리뷰 ID: {2}
                        - 리뷰 작성자 ID : {3}
                        - 리뷰 작성자 닉네임 : {4}
                        - 리뷰 메뉴: {5}
                        - 리뷰 내용: {6}
                        - 리뷰 날짜: {7}
                        *신고 INFO*
                        - 신고사유: {8}
                        - 신고내용: {9}
                        - 신고 날짜: {10}
                        ===================
                        
                        """
                .trimIndent()
        )
        val args = arrayOf<Any>(
            reporter.id!!,
            reporter.nickname!!,
            review.id,
            review.user.id!!,
            review.user.nickname!!,
            review.menu.name,
            review.content,
            review.updatedAt.toString(),
            report.reportType.description,
            report.content,
            report.createdAt.toString()
        )
        return messageFormat.format(args)
    }

    fun sendUserInquiry(inquiry: Inquiry): String {
        val messageFormat = MessageFormat(
            """
                        ===================
                        *문의 INFO*
                        - 문의자 ID: {0}
                        - 닉네임: {1}
                        - 이메일: {2}
                        *문의 내용*
                        - Date: {3}
                        - Content: {4}
                        ===================
                        
                        """
                .trimIndent()
        )
        val args = arrayOf<Any>(
            inquiry.user.id!!,
            inquiry.user.nickname!!,
            inquiry.email,
            inquiry.createdAt.toString(),
            inquiry.content
        )
        return messageFormat.format(args)
    }

}