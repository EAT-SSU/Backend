package com.ssu.eatssu.domain.report.entity

import com.ssu.eatssu.domain.review.entity.Review
import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
class Report(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    val review: Review,

    @Enumerated(EnumType.STRING)
    val reportType: ReportType,

    @Enumerated(EnumType.STRING)
    val reportStatus: ReportStatus,

    @Column(name = "report_content")
    val content: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    val id: Long? = null,
) : BaseEntity(){
    companion object {
        fun of(
            user: User,
            review: Review,
            reportType: String,
            content: String
        ): Report {
            return Report(
                user,
                review,
                ReportType.valueOf(reportType),
                ReportStatus.PENDING,
                content
            )
        }
    }
}