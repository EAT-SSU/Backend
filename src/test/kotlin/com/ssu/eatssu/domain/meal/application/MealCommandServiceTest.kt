package com.ssu.eatssu.domain.meal.application

import com.ssu.eatssu.domain.meal.persistence.MealRepository
import com.ssu.eatssu.domain.meal.presentation.dto.CreateMealRequest
import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import com.ssu.eatssu.global.enums.ErrorMessages
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
class MealCommandServiceTest @Autowired constructor(
    val mealCommandService: MealCommandService,
    val mealRepository: MealRepository,
    val menuRepository: MenuRepository
) {

    @BeforeEach
    fun setUp() {
        mealRepository.deleteAll()
        menuRepository.deleteAll()
    }

    @Test
    @DisplayName("식단을 생성한다")
    @Transactional
    fun createMealTest() {
        // given
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

        // when
        mealCommandService.createMeal(request)

        // then
        assertThat(mealRepository.findAll()).hasSize(1)
        assertThat(mealRepository.findAll().get(0).menus).hasSize(3)
        assertThat(mealRepository.findAll().get(0).menus[0].name).isEqualTo("돈가스")
        assertThat(mealRepository.findAll().get(0).menus[1].name).isEqualTo("김치 볶음밥")
        assertThat(mealRepository.findAll().get(0).menus[2].name).isEqualTo("깍두기")
    }

    @Test
    @DisplayName("식단을 제공하는 식당이 아니면 예외를 발생한다")
    fun invalidRestaurantTest() {
        // given & then
        assertThatThrownBy { val request = CreateMealRequest.from(
            "20240621",
            "FOOD_COURT",
            "LUNCH",
            "돈가스, 김치 볶음밥, 깍두기"
        ) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(ErrorMessages.INVALID_RESTAURANT.message)
    }
}