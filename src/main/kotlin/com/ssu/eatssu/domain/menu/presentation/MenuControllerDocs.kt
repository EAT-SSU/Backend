package com.ssu.eatssu.domain.menu.presentation

import com.ssu.eatssu.domain.menu.presentation.dto.GetMenusResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Menu", description = "메뉴 API")
interface MenuControllerDocs {
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
        ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
        ApiResponse(responseCode = "403", description = "FORBIDDEN"),
        ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    ])
    @Operation(summary = "메뉴 리스트 조회", description = "푸드코트, 스낵코너의 메뉴 리스트를 조회합니다.")
    fun getMenus(@RequestParam("restaurant") restaurant: String) : ResponseEntity<GetMenusResponse>
}