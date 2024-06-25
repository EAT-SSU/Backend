package com.ssu.eatssu.domain.user.entity

import com.ssu.eatssu.domain.oauth.entity.OAuthProvider
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(unique = true, name = "user_nickname")
    var nickname: String? = null,

    @Column(unique = true, name = "user_email")
    var email: String,

    val providerId: String,

    val credentials: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    var status: UserStatus,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_provider")
    val provider: OAuthProvider,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    val role: Role,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null,
) {

    companion object {
        fun initial(
            email: String,
            provider: OAuthProvider,
            providerId: String,
            credentials: String
        ): User {
            return User(
                email = email,
                provider = provider,
                providerId = providerId,
                credentials = credentials,
                status = UserStatus.ACTIVE,
                role = Role.ROLE_USER
            )
        }
    }

    fun changeNickname(newNickname: String) {
        this.nickname = newNickname
    }

    fun changeEmail(newEmail: String) {
        this.email = newEmail
    }
}