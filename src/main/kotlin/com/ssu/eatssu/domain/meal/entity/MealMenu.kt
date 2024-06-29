package com.ssu.eatssu.domain.meal.entity

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
class MealMenu(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    val meal: Meal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    val menu: Menu,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_menu_id")
    val id: Long? = null
) : BaseEntity()
