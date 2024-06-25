package com.ssu.eatssu.domain.user.application

import com.ssu.eatssu.domain.oauth.entity.OAuthProvider
import com.ssu.eatssu.domain.user.persistence.UserRepository
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
    @DisplayName("유저 생성을 테스트 한다")
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
}