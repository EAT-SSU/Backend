package com.ssu.eatssu.domain.menu.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssu.eatssu.domain.menu.application.MenuQueryService
import com.ssu.eatssu.domain.menu.presentation.dto.MenuCategoryListResponse
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import org.junit.jupiter.api.DisplayName
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.util.LinkedMultiValueMap
import kotlin.test.Test

@WebMvcTest(MenuController::class)
@ActiveProfiles("test")
class MenuControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @MockBean
    lateinit var menuQueryService: MenuQueryService

    @Test
    @DisplayName("메뉴 카테고리 별 조회 테스트")
    @WithMockUser
    fun getMenusTest() {
        val url = "/api/menus"

        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("restaurant", Restaurant.FOOD_COURT.name)

        whenever(menuQueryService.getMenusGroupedByCategory(Restaurant.FOOD_COURT))
            .thenReturn(MenuCategoryListResponse(listOf()))

        val requestActions = mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .queryParams(queryParams)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")
        )

        requestActions
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
    }
}