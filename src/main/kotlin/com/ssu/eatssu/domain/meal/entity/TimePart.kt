package com.ssu.eatssu.domain.meal.entity

enum class TimePart(
    val description: String
) {
    MORNING("조식"),
    LUNCH("중식"),
    DINNER("석식");

    companion object {
        fun from(timePartName: String): TimePart {
            return values().firstOrNull { it.name.equals(timePartName) }
                ?: throw IllegalArgumentException("TimePart not found")
        }
    }
}