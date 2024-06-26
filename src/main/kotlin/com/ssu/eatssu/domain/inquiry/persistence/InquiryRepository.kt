package com.ssu.eatssu.domain.inquiry.persistence

import com.ssu.eatssu.domain.inquiry.entity.Inquiry
import org.springframework.data.jpa.repository.JpaRepository

interface InquiryRepository : JpaRepository<Inquiry, Long> {
}