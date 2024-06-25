package com.ssu.eatssu.domain.user.entity

import org.springframework.security.core.GrantedAuthority

enum class Role(
    val authorityValue: String,
    val description: String
) : GrantedAuthority {
    ROLE_USER("ROLE_USER", "일반 사용자"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자");

    override fun getAuthority(): String {
        return authorityValue
    }
}