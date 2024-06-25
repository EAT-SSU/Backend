package com.ssu.eatssu.domain.user.application

import com.ssu.eatssu.domain.user.persistence.UserQueryDslRepository
import com.ssu.eatssu.domain.user.persistence.UserRepository
import com.ssu.eatssu.domain.user.presentation.dto.UserMypageResponse
import com.ssu.eatssu.global.enums.ErrorMessages
import com.ssu.eatssu.global.exception.NotExistsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQueryService (
    private val userRepository: UserRepository,
    private val userQueryDslRepository: UserQueryDslRepository
){

    fun getUserMypage(userId: Long) : UserMypageResponse{
        val user = userRepository.findById(userId).orElseThrow {
            NotExistsException(ErrorMessages.NOT_EXIST_USER.message)
        }

        return UserMypageResponse.of(user)

    }
}