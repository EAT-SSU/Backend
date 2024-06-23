package com.ssu.eatssu.domain.meal.entity

import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

@Entity
class Meal(
    val price: Int?,

    @DateTimeFormat(pattern = "yyyyMMdd")
    @Temporal(TemporalType.DATE)
    val date: Date,

    @Enumerated(EnumType.STRING)
    val restaurant: Restaurant,

    @Enumerated(EnumType.STRING)
    val timePart: TimePart,

    @OneToMany(mappedBy = "meal", cascade = [CascadeType.ALL])
    val mealItems: MutableList<MealItem> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    val id: Long? = null
) {
}