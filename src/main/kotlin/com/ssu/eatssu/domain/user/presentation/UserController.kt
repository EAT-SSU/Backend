package com.ssu.eatssu.domain.user.presentation

import com.ssu.eatssu.domain.user.application.UserCommandService
import com.ssu.eatssu.domain.user.application.UserQueryService
import com.ssu.eatssu.domain.user.application.UserValidator
import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserEmailRequest
import com.ssu.eatssu.domain.user.presentation.dto.ChangeUserNicknameRequest
import com.ssu.eatssu.domain.user.presentation.dto.UserMypageResponse
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userCommandService: UserCommandService,
    private val userQueryService: UserQueryService,
    private val userValidator: UserValidator
) : UserControllerDocs {
    override fun verifyDuplicatedEmail(email: String): ResponseEntity<Void> {
        userValidator.verifyDuplicatedEmail(email)
        return ResponseEntity.ok().build()
    }

    override fun verifyDuplicatedNickname(nickname: String): ResponseEntity<Void> {
        userValidator.verifyDuplicatedNickname(nickname)
        return ResponseEntity.ok().build()
    }

    override fun changeNickname(
        request: ChangeUserNicknameRequest,
        userDetails: CustomUserDetails
    ): ResponseEntity<Void> {
        userCommandService.changeNickname(userDetails.getId(), request)
        return ResponseEntity.ok().build()
    }

    override fun changeEmail(
        request: ChangeUserEmailRequest,
        userDetails: CustomUserDetails
    ): ResponseEntity<Void> {
        userCommandService.changeEmail(userDetails.getId(), request)
        return ResponseEntity.ok().build()
    }

    override fun deleteUser(userDetails: CustomUserDetails): ResponseEntity<Void> {
        userCommandService.deleteUser(userDetails.getId())
        return ResponseEntity.ok().build()
    }

    override fun getMyPage(userDetails: CustomUserDetails): ResponseEntity<UserMypageResponse> {
        return ResponseEntity.ok(userQueryService.getMyPage(userDetails.getId()))
    }
}