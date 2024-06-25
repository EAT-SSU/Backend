package com.ssu.eatssu.domain.user.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserQueryDslRepository (
    private val queryFactory: JPAQueryFactory
){
}