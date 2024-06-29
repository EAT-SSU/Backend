package com.ssu.eatssu.domain.review.event

import org.springframework.context.ApplicationEvent

class CreateReviewEvent(
    source: Any,
    val menuId: Long,
    val mainRating: Int,
) : ApplicationEvent(source)