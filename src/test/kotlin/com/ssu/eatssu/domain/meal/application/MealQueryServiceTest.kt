package com.ssu.eatssu.domain.meal.application

import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.meal.presentation.dto.CreateMealRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsRequest
import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
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

    @BeforeEach
    fun setUp() {
        menuRepository.deleteAll()
    }

    @Test
    @DisplayName("식단을 조회한다")
    @Transactional
    fun getMealTest() {
        // given
        createMeal()

        val request = GetMealsRequest.from(
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

    private fun createMeal(): Meal {
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

        return mealCommandService.createMeal(request)
    }

    @Test
    @DisplayName("식단 내 메뉴를 조회한다")
    @Transactional
    fun getMenusInMealTest() {
        // given
        val meal = createMeal()

        // when
        val response = mealQueryService.getMenusInMeal(meal.id!!)

        // then
        assertThat(response.menus[0].menuName).isEqualTo("돈가스")
        assertThat(response.menus[1].menuName).isEqualTo("김치 볶음밥")
        assertThat(response.menus[2].menuName).isEqualTo("깍두기")
    }
}
