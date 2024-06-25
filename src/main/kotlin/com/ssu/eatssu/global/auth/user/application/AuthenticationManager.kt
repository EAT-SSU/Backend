package com.ssu.eatssu.global.auth.user.application

import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

@Component
class AuthenticationManager(
    private val userRepository: UserRepository
) {
    fun getAuthentication(email: String): UsernamePasswordAuthenticationToken? {
        userRepository.findByEmail(email)
            .apply {
                return this?.let { authenticate(it) }
            }
    }

    private fun authenticate(user: User): UsernamePasswordAuthenticationToken {
        val userDetails = CustomUserDetails(user)
        return UsernamePasswordAuthenticationToken(
            userDetails,
            user.credentials,
            userDetails.authorities
        )
    }


}