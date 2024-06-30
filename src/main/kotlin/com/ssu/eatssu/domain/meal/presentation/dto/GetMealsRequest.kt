package com.ssu.eatssu.domain.meal.presentation.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.ssu.eatssu.domain.meal.entity.TimePart
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.enums.ErrorMessages
import java.text.SimpleDateFormat
import java.util.Date

data class GetMealsRequest(
    val date: Date,
    val restaurant: Restaurant,
    val timePart: TimePart
) {
    companion object {
        @JvmStatic
        @JsonCreator
        fun from(
            @JsonProperty("date") date: String,
            @JsonProperty("restaurant") restaurant: String,
            @JsonProperty("timePart") timePart: String,
        ): GetMealsRequest {
            val formattedDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
            val restaurant = Restaurant.from(restaurant)
            val timePartEnum = TimePart.from(timePart)

            require(restaurant.isMealRestaurant()) {
                ErrorMessages.INVALID_RESTAURANT.message
            }

            return GetMealsRequest(
                date = formattedDate,
                restaurant = restaurant,
                timePart = timePartEnum,
            )
        }
    }

}
