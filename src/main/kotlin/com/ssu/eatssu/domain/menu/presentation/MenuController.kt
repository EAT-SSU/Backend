package com.ssu.eatssu.domain.menu.presentation

import com.ssu.eatssu.domain.menu.application.MenuQueryService
import com.ssu.eatssu.domain.menu.presentation.dto.MenuCategoryListResponse
import com.ssu.eatssu.domain.restaurant.entity.Restaurant
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/menus")
class MenuController(
    private val menuQueryService: MenuQueryService
) : MenuControllerDocs {

    @GetMapping
    override fun getMenus(
        @RequestParam restaurant: Restaurant): ResponseEntity<MenuCategoryListResponse> {
        return ResponseEntity(menuQueryService.getMenusGroupedByCategory(restaurant), HttpStatus.OK)
    }
}