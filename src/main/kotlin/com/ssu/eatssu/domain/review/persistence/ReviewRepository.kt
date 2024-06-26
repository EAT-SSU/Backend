package com.ssu.eatssu.domain.review.persistence

import com.ssu.eatssu.domain.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>