package com.ssu.eatssu.domain.menu.entity

import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import jakarta.persistence.*

@Entity
class Menu(
    @Column(nullable = false, unique = true, name = "menu_name")
    var name: String,

    @Column(name = "menu_price")
    var price: Int?,

    @Column(name = "menu_rating")
    var rating: Double? = 0.0,

    @Enumerated(EnumType.STRING)
    val restaurant: Restaurant,

    @Enumerated(EnumType.STRING)
    var menuCategory: MenuCategory?,

    @Enumerated(EnumType.STRING)
    var menuStatus: MenuStatus,

    @Enumerated(EnumType.STRING)
    var menuType: MenuType,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "meal_id")
    var meal: Meal? = null,

    // todo : Reviews 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    val id: Long? = null,
) {
    companion object {
        fun initial(
            name: String,
            price: Int,
            restaurantName: String,
            categoryName: String,
            menuType: String
        ): Menu {
            return Menu(
                name,
                price,
                0.0,
                Restaurant.from(restaurantName),
                MenuCategory.from(categoryName),
                MenuStatus.AVAILABLE,
                MenuType.from(menuType)
            )
        }

        fun fixture(
            name: String,
            price: Int? = 5000,
            rating: Double? = 0.0,
            restaurant: Restaurant = Restaurant.SNACK_CORNER,
            menuCategory: MenuCategory? = MenuCategory.SNACK,
            menuStatus: MenuStatus = MenuStatus.AVAILABLE,
            menuType: MenuType = MenuType.ITEM
        ): Menu {
            return Menu(name, price, rating, restaurant, menuCategory, menuStatus, menuType)
        }
    }
}