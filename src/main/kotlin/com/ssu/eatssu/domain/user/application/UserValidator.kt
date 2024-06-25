package com.ssu.eatssu.domain.user.application

import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.global.enums.ErrorMessages
import org.springframework.stereotype.Component

@Component
class UserValidator(private val userRepository: UserRepository) {

    fun verifyDuplicatedNickname(nickname: String) {
        if (userRepository.findByNickname(nickname) != null) {
            throw IllegalArgumentException(ErrorMessages.ALREADY_EXIST_NICKNAME.message)
        }
    }

    fun verifyDuplicatedEmail(email: String) {
        if (userRepository.findByEmail(email) != null) {
            throw IllegalArgumentException(ErrorMessages.ALREADY_EXIST_EMAIL.message)
        }
    }
}
