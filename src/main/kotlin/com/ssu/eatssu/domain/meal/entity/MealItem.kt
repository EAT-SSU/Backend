package com.ssu.eatssu.domain.meal.entity

import com.ssu.eatssu.domain.menu.entity.Menu
import jakarta.persistence.*

@Entity
class MealItem(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    val meal: Meal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    val menu: Menu,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_item_id")
    val id: Long? = null
)