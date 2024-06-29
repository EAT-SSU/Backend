package com.ssu.eatssu.domain.menu.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.entity.QMenu.menu
import com.ssu.eatssu.domain.menu.presentation.dto.MenuCategoryListResponse
import com.ssu.eatssu.domain.menu.presentation.dto.CategoryWithMenusResponse
import com.ssu.eatssu.domain.menu.presentation.dto.MenuDetailResponse
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

    fun findAllMenusGroupedByCategory(
        restaurantName: String
    ): MenuCategoryListResponse {
        // Restaurant에 해당하는 모든 메뉴를 가져옵니다.
        val menus = queryFactory.select(menu)
            .from(menu)
            .where(menu.restaurant.eq(Restaurant.from(restaurantName)))
            .fetch()

        // 메뉴를 카테고리 별로 그룹화 합니다.
        val groupedMenus = menus.groupBy { it.menuCategory }
        val categories = groupedMenus.keys.toList()

        // 그룹화 된 카테고리를 이용하여 MenuCategoryResponse를 생성합니다.
        return MenuCategoryListResponse.of(categories.mapNotNull { category ->
            groupedMenus[category]?.let { menuList ->
                CategoryWithMenusResponse.of(
                    category = category!!.name,
                    menus = menuList.map {
                        MenuDetailResponse(
                            id = it.id!!,
                            name = it.name,
                            price = it.price ?: 0,
                            rating = it.calculateRating(),
                            menuStatus = it.menuStatus
                        )
                    }
                )
            }
        }
        )
    }
}