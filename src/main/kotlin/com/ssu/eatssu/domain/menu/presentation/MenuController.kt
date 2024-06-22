package com.ssu.eatssu.domain.menu.presentation

import com.ssu.eatssu.domain.menu.presentation.dto.GetMenusResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/menus")
class MenuController : MenuControllerDocs {
    @GetMapping
    override fun getMenus(restaurant: String): ResponseEntity<GetMenusResponse> {
        return ResponseEntity.ok(GetMenusResponse("푸드코트", listOf()))
    }
}