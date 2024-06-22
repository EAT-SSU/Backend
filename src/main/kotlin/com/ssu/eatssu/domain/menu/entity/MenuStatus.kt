package com.ssu.eatssu.domain.menu.entity

enum class MenuStatus(
    description: String
) {
    AVAILABLE("판매중"),
    DISCONTINUED("단종"),
    SOLD_OUT("품절")
}