package com.ssu.eatssu.domain.menu.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.entity.QMenu.menu
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import org.springframework.stereotype.Component

@Component
class MenuQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun find(menuName: String, restaurant: Restaurant? = null): Menu? {
        return queryFactory.select(menu)
            .from(menu)
            .where(menu.name.eq(menuName),
                restaurant?.let { menu.restaurant.eq(restaurant) }
            )
            .limit(1)
            .fetchOne()
    }

}