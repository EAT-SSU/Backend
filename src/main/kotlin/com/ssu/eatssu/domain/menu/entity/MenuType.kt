package com.ssu.eatssu.domain.menu.entity

import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException

enum class MenuType {
    ITEM,
    SET;

    companion object {
        fun from(typeName: String): MenuType {
            return entries.firstOrNull { it.name.equals(typeName) }
                ?: throw NotExistsException(ErrorMessages.NOT_EXIST_TYPE.message)
        }
    }
}