package com.ssu.eatssu.domain.meal.presentation.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.ssu.eatssu.domain.meal.entity.TimePart
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.enums.ErrorMessages
import org.springframework.util.Assert
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

data class CreateMealRequest(
    @JsonProperty("date")
    val date: Date,
    @JsonProperty("restaurantName")
    val restaurant: Restaurant,
    @JsonProperty("timePart")
    val timePart: TimePart,
    @JsonProperty("menus")
    val menus: List<String>
) {
    companion object {
        @JvmStatic
        @JsonCreator
        fun from(
            @JsonProperty("date") date: String,
            @JsonProperty("restaurantName") restaurantName: String,
            @JsonProperty("timePart") timePart: String,
            @JsonProperty("menus") menus: String
        ): CreateMealRequest {
            val formattedDate = SimpleDateFormat("yyyyMMdd").parse(date)
            val restaurant = Restaurant.from(restaurantName)
            val timePartEnum = TimePart.from(timePart)
            val menuList = menus.split(",").map { it.trim() }

            require(restaurant.isMealRestaurant()) {
                ErrorMessages.INVALID_RESTAURANT.message
            }

            return CreateMealRequest(
                date = formattedDate,
                restaurant = restaurant,
                timePart = timePartEnum,
                menus = menuList
            )
        }
    }
}
