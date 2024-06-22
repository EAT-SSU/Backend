package com.ssu.eatssu.domain.menu.application

import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.menu.presentation.dto.CreateMenuRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class MenuCommandServiceTest @Autowired constructor(
    private val menuCommandService: MenuCommandService,
    private val menuRepository: MenuRepository
) {
    @BeforeEach
    fun setUp() {
        menuRepository.deleteAll()
    }

    @Test
    @DisplayName("메뉴 저장 테스트")
    fun saveMenuTest() {
        // given
        val request = CreateMenuRequest(
            name = "떡볶이",
            price = 10000,
            restaurantName = "SNACK_CORNER",
        )

        // when
        menuCommandService.saveMenu(request)

        // then
        val menu = menuRepository.findAll()
        assertEquals(1, menu.size)
        assertEquals(request.name, menu[0].name)
        assertEquals(request.price, menu[0].price)
        assertEquals(request.restaurantName, menu[0].restaurant.name)
        assertEquals(request.menuType, menu[0].menuType.name)
        assertEquals(request.menuStatus, menu[0].menuStatus.name)
    }
}
