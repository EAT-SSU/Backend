package com.ssu.eatssu.domain.user.persistence

import com.ssu.eatssu.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByNickname(nickname: String): User?
    fun findByEmail(email: String): User?
    fun findByProviderId(providerId: String): User?
}