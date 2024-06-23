package com.ssu.eatssu.domain.menu.application

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.persistence.MenuQuerydslRepository
import com.ssu.eatssu.domain.menu.presentation.dto.MenuCategoryListResponse
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MenuQueryService(
    private val menuQuerydslRepository: MenuQuerydslRepository
) {
    fun getMenu(menuName: String, restaurant: Restaurant): Menu {
        return menuQuerydslRepository.find(menuName, restaurant)
            ?: throw NotExistsException(ErrorMessages.NOT_FOUND_MENU.message)
    }

    // todo: Restaurant을 Enum으로 변경할 수도 있습니다.
    fun getMenusGroupedByCategory(restaurantName: String): MenuCategoryListResponse {
        return menuQuerydslRepository.findAllMenusGroupedByCategory(restaurantName)
    }
}
