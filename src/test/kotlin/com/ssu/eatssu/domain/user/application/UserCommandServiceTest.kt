package com.ssu.eatssu.domain.user.application

import com.ssu.eatssu.domain.oauth.entity.OAuthProvider
import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserEmailRequest
import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserNicknameRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
class UserCommandServiceTest @Autowired constructor(
    val userCommandService: UserCommandService,
    val userRepository: UserRepository
) {

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저를 생성한다")
    fun createUserTest() {
        // given
        var email = "test@test.com"
        var provider = OAuthProvider.EATSSU
        var providerId = "1234"

        // when
        var user = userCommandService.createUser(email, provider, providerId)

        // then
        assertNotNull(user)
        assertEquals(email, user.email)
        assertEquals(provider, user.provider)
        assertEquals(providerId, user.providerId)
    }

    @Test
    @DisplayName("닉네임을 변경한다")
    fun changeNicknameTest() {
        // given
        val user = userRepository.save(User.fixture())
        val newNickname = "newNickname"

        // when
        userCommandService.changeNickname(user.id!!, ChangeUserNicknameRequest(newNickname))

        // then
        userRepository.findById(user.id!!)
            .apply {
                assertEquals(newNickname, this.get().nickname)
            }
    }

    @Test
    @DisplayName("이메일을 변경한다")
    fun changeEmailTest() {
        // given
        val user = userRepository.save(User.fixture())
        val newEmail = "newEmail"

        // when
        userCommandService.changeEmail(user.id!!, ChangeUserEmailRequest(newEmail))

        // then
        userRepository.findById(user.id!!)
            .apply {
                assertEquals(newEmail, this.get().email)
            }
    }
}