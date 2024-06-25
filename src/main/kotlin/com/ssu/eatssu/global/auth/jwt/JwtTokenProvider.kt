package com.ssu.eatssu.global.auth.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {

    var secretKey: String = "58244814c55d300622bf0c14b398221f783ed70440df53f001e4366683b9ee26a532652096d0acce7c8b01929dd0b49305442f3f18039e30781898befdc5696b"
    var accessTokenExpiration: Long = 3600000
    var refreshTokenExpiration: Long = 2592000000
    fun createTokens(email: String): Tokens {
        return Tokens(
            createToken(email, accessTokenExpiration),
            createToken(email, refreshTokenExpiration)
        )
    }

    fun createToken(
        subject: String,
        expiration: Long
    ): String {
        val now = System.currentTimeMillis()

        return Jwts.builder()
            .setIssuedAt(Date(now))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setExpiration(Date(now + expiration))
            .setSubject(subject)
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .compact()
    }

    fun verifyToken(token: String): Boolean {
        return try {
            !parseClaims(token).expiration.before(Date())
            true
        } catch (e: Exception) {
            false
        }
    }

    fun parseClaims(token: String): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
        } catch (e: ExpiredJwtException) {
            return e.claims
        }
    }

    fun getSubject(token: String): String {
        return parseClaims(token).subject
    }
}