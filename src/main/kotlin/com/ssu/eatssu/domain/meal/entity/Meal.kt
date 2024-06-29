package com.ssu.eatssu.domain.meal.entity

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.entity.BaseEntity
import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.Date

@Entity
class Meal(
    val price: Int?,
    var rating: Double? = 0.0,

    @DateTimeFormat(pattern = "yyyyMMdd")
    @Temporal(TemporalType.DATE)
    val date: Date,

    @Enumerated(EnumType.STRING)
    val restaurant: Restaurant,

    @Enumerated(EnumType.STRING)
    val timePart: TimePart,

    @ElementCollection
    val menuNames: List<String> = mutableListOf(),

    @OneToMany(mappedBy = "meal", cascade = [CascadeType.ALL])
    val mealMenus: MutableList<MealMenu> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    val id: Long? = null
) : BaseEntity() {
    companion object {
        fun of(
            date: Date,
            rating: Double? = 0.0,
            restaurant: Restaurant,
            timePart: TimePart,
            menuNames: List<String>,
            menus: List<Menu>
        ): Meal {
            val meal = Meal(
                restaurant.price,
                rating,
                date,
                restaurant,
                timePart,
                menuNames,
                mutableListOf()
            )
            menus.forEach { menu ->
                meal.mealMenus.add(MealMenu(meal, menu))
            }
            return meal
        }
    }
}
