package com.ssu.eatssu.domain.menu.application

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.entity.MenuCategory
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
class MenuQueryServiceTest @Autowired constructor(
    val menuQueryService: MenuQueryService,
    val menuRepository: MenuRepository
) {

    @BeforeEach
    fun setUp() {
        menuRepository.deleteAll()
    }

    @Test
    @DisplayName("카테고리 별 메뉴 리스트 조회 테스트")
    fun getMenusGroupedByCategoryTest() {
        // given
        val restaurantName = "FOOD_COURT"
        menuRepository.saveAll(
            listOf(
                Menu.fixture("팟타이", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.FUSION),
                Menu.fixture("쇠고기 쌀국수", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.FUSION),
                Menu.fixture("삼선 짬뽕", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.FUSION),
                Menu.fixture("짬뽕 밥", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.FUSION),
                Menu.fixture("치킨 찹 스테이크 덮밥", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.FUSION),
                Menu.fixture("순대 국밥", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.SOUP),
                Menu.fixture("얼큰 순대국", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.SOUP),
                Menu.fixture("고기 국밥", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.SOUP),
                Menu.fixture("떡볶이", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.SNACK),
                Menu.fixture("순대", 7000, restaurant = Restaurant.FOOD_COURT, menuCategory = MenuCategory.SNACK),
            )
        )

        // when
        val getMenusResponse = menuQueryService.getMenusGroupedByCategory(restaurant = Restaurant.FOOD_COURT)

        // then
        assertThat(getMenusResponse.categories.size).isEqualTo(3)
        assertThat(getMenusResponse.categories[0].category).isEqualTo("FUSION")
        assertThat(getMenusResponse.categories[0].menus.size).isEqualTo(5)
        assertThat(getMenusResponse.categories[1].category).isEqualTo("SOUP")
        assertThat(getMenusResponse.categories[1].menus.size).isEqualTo(3)
        assertThat(getMenusResponse.categories[2].category).isEqualTo("SNACK")
        assertThat(getMenusResponse.categories[2].menus.size).isEqualTo(2)
    }
}