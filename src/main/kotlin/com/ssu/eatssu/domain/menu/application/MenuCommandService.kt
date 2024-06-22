package com.ssu.eatssu.domain.menu.application

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.menu.presentation.dto.CreateMenuRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MenuCommandService(
    val menuRepository: MenuRepository
) {
    @Transactional
    fun saveMenu(request: CreateMenuRequest) {
        val menu = Menu.of(
            request.name,
            request.price,
            request.restaurantName,
            request.categoryName,
            request.menuType,
        )

        menuRepository.save(menu)
    }
}