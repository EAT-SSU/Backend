package com.ssu.eatssu.domain.meal.presentation.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.ssu.eatssu.domain.meal.entity.TimePart
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.enums.ErrorMessages
import org.springframework.util.Assert
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CreateMealRequest(
    @JsonProperty("date")
    val date: LocalDate,
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
            val formattedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"))
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
