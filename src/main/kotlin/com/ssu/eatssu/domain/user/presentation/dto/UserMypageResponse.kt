package com.ssu.eatssu.domain.user.presentation.dto

import com.ssu.eatssu.domain.user.entity.User

data class UserMypageResponse(
    val nickname: String,
    val provider: String
) {
    companion object {
        fun of(user: User): UserMypageResponse {
            return UserMypageResponse(user.nickname!!, user.provider.name)
        }
    }
}
