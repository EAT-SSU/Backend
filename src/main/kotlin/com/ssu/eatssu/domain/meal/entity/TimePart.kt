package com.ssu.eatssu.domain.meal.entity

import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException

enum class TimePart(
    val description: String
) {
    MORNING("조식"),
    LUNCH("중식"),
    DINNER("석식");

    companion object {
        fun from(timePartName: String): TimePart {
            return values().firstOrNull { it.name.equals(timePartName) }
                ?: throw NotExistsException(ErrorMessages.NOT_EXIST_TIMEPART.message)
        }
    }
}