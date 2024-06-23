package com.ssu.eatssu.domain.meal.entity

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.Date

@Entity
class Meal(
    val price: Int?,

    @DateTimeFormat(pattern = "yyyyMMdd")
    @Temporal(TemporalType.DATE)
    val date: LocalDate,

    @Enumerated(EnumType.STRING)
    val restaurant: Restaurant,

    @Enumerated(EnumType.STRING)
    val timePart: TimePart,

    @OneToMany(mappedBy = "meal", cascade = [CascadeType.ALL])
    val menus: MutableList<Menu> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    val id: Long? = null
) {
    companion object {
        fun of(
            date: LocalDate,
            restaurant: Restaurant,
            timePart: TimePart,
            menus: List<Menu>
        ): Meal {
            return Meal(
                restaurant.price,
                date,
                restaurant,
                timePart,
                menus.toMutableList()
            )
        }
    }
}