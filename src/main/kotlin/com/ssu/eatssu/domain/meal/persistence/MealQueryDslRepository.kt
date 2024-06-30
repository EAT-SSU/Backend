package com.ssu.eatssu.domain.meal.persistence

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.meal.entity.QMeal.meal
import com.ssu.eatssu.domain.meal.entity.QMealMenu.mealMenu
import com.ssu.eatssu.domain.meal.entity.TimePart
import com.ssu.eatssu.domain.menu.entity.QMenu.menu
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import org.springframework.stereotype.Component
import java.util.Date

@Component
class MealQueryDslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun find(
        id: Long? = null,
        date: Date? = null,
        restaurant: Restaurant? = null,
        timePart: TimePart? = null
    ): List<Meal>? {
        val builder = BooleanBuilder()

        id?.let {
            builder.and(meal.id.eq(id))
        }

        if (id == null) {
            builder.and(
                meal.date.eq(date)
                    .and(meal.restaurant.eq(restaurant))
                    .and(meal.timePart.eq(timePart))
            )
        }

        return queryFactory
            .selectFrom(meal)
            .distinct()
            .leftJoin(meal.mealMenus, mealMenu).fetchJoin()
            .leftJoin(mealMenu.menu, menu).fetchJoin()
            .where(
                builder
            )
            .fetch()
    }
}