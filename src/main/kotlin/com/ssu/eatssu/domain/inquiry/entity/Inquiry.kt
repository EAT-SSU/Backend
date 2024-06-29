package com.ssu.eatssu.domain.inquiry.entity

import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "inquiry")
class Inquiry(
    @Column(name = "inquiry_title")
    val title: String,

    @Column(name = "inquiry_content")
    val content: String,

    @Column(name = "inquiry_email")
    val email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Enumerated(EnumType.STRING)
    val inquiryStatus: InquiryStatus,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    val id: Long? = null,
) : BaseEntity() {
    companion object {
        fun of(
            user: User,
            title: String,
            content: String,
            email: String
        ): Inquiry {
            return Inquiry(
                title,
                content,
                email,
                user,
                InquiryStatus.HOLD
            )
        }
    }
}