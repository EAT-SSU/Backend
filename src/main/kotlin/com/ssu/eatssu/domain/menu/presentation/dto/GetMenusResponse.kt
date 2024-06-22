package com.ssu.eatssu.domain.menu.presentation.dto

data class GetMenusResponse(val category: String, val menus: List<MenuDetailResponse> )

data class MenuDetailResponse(val id: Long, val name: String, val price: Int, val rating: Double)
