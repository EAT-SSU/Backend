package com.ssu.eatssu.domain.menu.entity

import com.ssu.eatssu.domain.meal.entity.MealItem
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import jakarta.persistence.*

@Entity
class Menu(
    var name: String,
    var price: Int?,
    var rating: Double? = 0.0,

    @Enumerated(EnumType.STRING)
    val restaurant: Restaurant,

    @Enumerated(EnumType.STRING)
    var menuCategory: MenuCategory?,

    @Enumerated(EnumType.STRING)
    var menuStatus: MenuStatus,

    @Enumerated(EnumType.STRING)
    var menuType: MenuType,

    // todo : Reviews 추가

    @OneToMany(mappedBy = "menu", cascade = [CascadeType.ALL])
    val mealItems: MutableList<MealItem> = mutableListOf(),

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
            price: Int?,
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