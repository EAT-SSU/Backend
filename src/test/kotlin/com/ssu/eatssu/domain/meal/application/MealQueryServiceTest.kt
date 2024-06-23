package com.ssu.eatssu.domain.meal.application

import com.ssu.eatssu.domain.meal.persistence.MealRepository
import com.ssu.eatssu.domain.meal.presentation.dto.CreateMealRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealRequest
import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
class MealQueryServiceTest @Autowired constructor(
    private val mealQueryService: MealQueryService,
    private val mealCommandService: MealCommandService,
    private val menuRepository: MenuRepository
) {

    @Test
    @DisplayName("식단을 조회한다")
    @Transactional
    fun getMealTest() {
        // given
        createMeal()

        val request = GetMealRequest.from(
            "20240621",
            "DORMITORY",
            "LUNCH"
        )

        // when
        val response = mealQueryService.getMeals(request)

        // then
        assertThat(response.meals).hasSize(1)
        assertThat(response.meals[0].menus).hasSize(3)
        assertThat(response.meals[0].menus[0].menuName).isEqualTo("돈가스")
        assertThat(response.meals[0].menus[1].menuName).isEqualTo("김치 볶음밥")
        assertThat(response.meals[0].menus[2].menuName).isEqualTo("깍두기")
    }

    private fun createMeal() {
        menuRepository.saveAll(
            listOf(
                Menu.fixture("돈가스", 5000, restaurant = Restaurant.DORMITORY),
                Menu.fixture("김치 볶음밥", 5000, restaurant = Restaurant.DORMITORY),
                Menu.fixture("깍두기", 5000, restaurant = Restaurant.DORMITORY)
            )
        )

        val request = CreateMealRequest.from(
            "20240621",
            "DORMITORY",
            "LUNCH",
            "돈가스, 김치 볶음밥, 깍두기"
        )

        mealCommandService.createMeal(request)
    }
}
