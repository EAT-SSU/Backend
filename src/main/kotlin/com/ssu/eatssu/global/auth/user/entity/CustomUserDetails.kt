package com.ssu.eatssu.global.auth.user.entity

import com.ssu.eatssu.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User
) : UserDetails {
    private val id: Long = user.id!!
    private val email: String = user.email
    private val credential: String = user.credentials
    private val role: GrantedAuthority = user.role

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return mutableListOf(role)
    }

    fun getId(): Long {
        return id
    }

    override fun getPassword(): String {
        return credential
    }

    override fun getUsername(): String {
        return email
    }
}