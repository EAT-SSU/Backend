package com.ssu.eatssu.domain.user.application

import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.domain.user.persistence.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserQueryServiceTest @Autowired constructor(
    private val userQueryService: UserQueryService,
    private val userRepository: UserRepository,
) {

    @Test
    @DisplayName("유저 마이페이지를 조회한다")
    fun getUserMypageTest() {
        // given
        val user = userRepository.save(User.fixture())

        // when
        val response = userQueryService.getMyPage(user.id!!)

        // then
        assertEquals(user.nickname, response.nickname)
        assertEquals(user.provider.name, response.provider)
    }
}
