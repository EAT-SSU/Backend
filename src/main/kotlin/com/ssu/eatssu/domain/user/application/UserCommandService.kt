package com.ssu.eatssu.domain.user.application

import com.ssu.eatssu.domain.oauth.entity.OAuthProvider
import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserEmailRequest
import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserNicknameRequest
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserCommandService(
    private val userRepository: UserRepository,
    private val userValidator: UserValidator,
    private val passwordEncoder: PasswordEncoder,
) {
    fun createUser(
        email: String,
        provider: OAuthProvider,
        providerId: String
    ): User {
        userValidator.verifyDuplicatedEmail(email)

        val credentials = encodeCredentials(provider, providerId)
        val user = User.initial(email, provider, providerId, credentials)

        return userRepository.save(user)
    }

    private fun encodeCredentials(provider: OAuthProvider, providerId: String): String {
        return passwordEncoder.encode(providerId + provider)
    }

    fun changeNickname(userId: Long, request: ChangeUserNicknameRequest) {
        userValidator.verifyDuplicatedNickname(request.newNickname)

        val user = userRepository.findById(userId).orElseThrow {
            NotExistsException(ErrorMessages.NOT_EXIST_USER.message)
        }

        user.changeNickname(request.newNickname)
    }

    fun changeEmail(userId: Long, request: ChangeUserEmailRequest) {
        userValidator.verifyDuplicatedNickname(request.newEmail)

        val user = userRepository.findById(userId).orElseThrow {
            NotExistsException(ErrorMessages.NOT_EXIST_USER.message)
        }

        user.changeEmail(request.newEmail)
    }

    fun deleteUser(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow {
            NotExistsException(ErrorMessages.NOT_EXIST_USER.message)
        }

        userRepository.delete(user)
    }
}