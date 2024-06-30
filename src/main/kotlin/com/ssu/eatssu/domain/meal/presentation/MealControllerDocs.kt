package com.ssu.eatssu.domain.meal.presentation

import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.meal.entity.TimePart
import com.ssu.eatssu.domain.meal.presentation.dto.CreateMealRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsRequest
import com.ssu.eatssu.domain.meal.presentation.dto.GetMealsResponse
import com.ssu.eatssu.domain.meal.presentation.dto.MenusInMealResponse
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Tag(name = "Meal", description = "식단 API")
interface MealControllerDocs {
    @Operation(
        summary = "변동 메뉴 식단 리스트 조회", description = """
            변동 메뉴 식단 리스트를 조회하는 API 입니다.
            변동 메뉴 식당 (학생 식당, 도담, 기숙사 식당) 의 특정날짜(yyyyMMdd), 특정시간대(아침/점심/저녁)에 해당하는 식단 목록을 조회합니다.
            일반적으로 학생식당과 도담의 경우 식단 여러개가 조회되고 기숙사식당은 한개만 조회됩니다.)
            
            """
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "식단 리스트 조회 성공"
        ), ApiResponse(
            responseCode = "400",
            description = "지원 하지 않는 식당 (고정 메뉴 식당)",
        ), ApiResponse(
            responseCode = "404",
            description = "존재 하지 않는 식당",
        )]
    )
    fun getMeals(
        date: Date,
        restaurant: Restaurant,
        timePart: TimePart
    ): ResponseEntity<GetMealsResponse>

    @Operation(
        summary = "식단 추가", description = """
            식단을 추가하는 API 입니다.<br><br>
            변동메뉴 식당(학생식당, 도담, 기숙사)의 특정날짜(yyyyMMdd), 특정시간대(아침/점심/저녁)에 해당하는 식단을 추가합니다.<br><br>
            이미 존재하는 식단일 경우 중복저장 되지 않도록 처리합니다.
            """
    )
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "식단 추가 성공"), ApiResponse(
            responseCode = "400",
            description = "지원 하지 않는 식당 (도담, 기숙사, 학생 식당만 지원)",
        ), ApiResponse(
            responseCode = "400",
            description = "잘못된 날짜 형식",
        ), ApiResponse(
            responseCode = "404",
            description = "존재 하지 않는 식당",
        )]
    )
    fun createMeal(
        @RequestBody createMealRequest: CreateMealRequest
    ): ResponseEntity<Meal>

    @Operation(
        summary = "식단 내 메뉴 조회", description = """
            식단에 포함된 메뉴 리스트를 조회하는 API 입니다.<br><br>
            특정 식단에 포함된 메뉴 리스트를 조회합니다.
        """
    )
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "식단 내 메뉴 조회 성공"), ApiResponse(
            responseCode = "400",
            description = "잘못된 식단 ID",
        ), ApiResponse(
            responseCode = "404",
            description = "존재 하지 않는 식단",
        )]
    )
    fun getMenusInMeal(
        mealId: Long
    ): ResponseEntity<MenusInMealResponse>

    @Operation(
        summary = "식단 삭제", description = """
            식단을 삭제하는 API 입니다.<br><br>
            식단 ID를 통해 특정 식단을 삭제합니다.
        """
    )
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "식단 내 메뉴 조회 성공"), ApiResponse(
            responseCode = "400",
            description = "잘못된 식단 ID",
        ), ApiResponse(
            responseCode = "404",
            description = "존재 하지 않는 식단",
        )]
    )
    fun deleteMeal(
        mealId: Long
    ): ResponseEntity<Void>
}