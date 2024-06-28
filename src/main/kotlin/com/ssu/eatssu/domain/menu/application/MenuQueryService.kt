package com.ssu.eatssu.domain.menu.application

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.persistence.MenuQuerydslRepository
import com.ssu.eatssu.domain.menu.presentation.dto.MenuCategoryListResponse
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException
import org.springframework.cache.annotation.Cacheable
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

    fun getMenu(menuName: String): Menu {
        return menuQuerydslRepository.find(menuName)
            ?: throw NotExistsException(ErrorMessages.NOT_FOUND_MENU.message)
    }

    @Cacheable(value = ["menus"], key = "#restaurant.name")
    fun getMenusGroupedByCategory(restaurant: Restaurant): MenuCategoryListResponse {
        return menuQuerydslRepository.findAllMenusGroupedByCategory(restaurant.name)
    }
}
