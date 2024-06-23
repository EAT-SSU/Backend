package com.ssu.eatssu.domain.menu.presentation.dto

data class MenuCategoryListResponse(val categories: List<CategoryWithMenusResponse>) {
    companion object {
        fun of(categories: List<CategoryWithMenusResponse>): MenuCategoryListResponse {
            return MenuCategoryListResponse(categories)
        }
    }
}

data class CategoryWithMenusResponse(val category: String, val menus: List<MenuDetailResponse>) {
    companion object {
        fun of(category: String, menus: List<MenuDetailResponse>): CategoryWithMenusResponse {
            return CategoryWithMenusResponse(category, menus)
        }
    }
}

data class MenuDetailResponse(val id: Long, val name: String, val price: Int, val rating: Double)
