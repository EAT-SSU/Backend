package com.ssu.eatssu.domain.menu.entity

enum class MenuStatus(
    val description: String
) {
    AVAILABLE("판매중"),
    DISCONTINUED("단종"),
    NONE("미정"),
    SOLD_OUT("품절")
}