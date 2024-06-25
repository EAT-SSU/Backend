package com.ssu.eatssu.domain.oauth.application

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.ssu.eatssu.domain.oauth.client.AppleClient
import com.ssu.eatssu.domain.oauth.presentation.dto.AppleKeys
import com.ssu.eatssu.domain.oauth.presentation.dto.Key
import com.ssu.eatssu.domain.oauth.presentation.dto.OAuthLoginRequest
import com.ssu.eatssu.global.enums.ErrorMessages
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

@Component
class AppleAuthenticator(
    private val appleClient: AppleClient
) {
    fun toOAuthLoginRequest(identityToken: String): OAuthLoginRequest {
        val publicKey = generatePublicKey(identityToken)
        return extractEmailAndProviderIdByPublicKey(identityToken, publicKey)
    }

    private fun extractEmailAndProviderIdByPublicKey(identityToken: String,
                                                     publicKey: PublicKey): OAuthLoginRequest {
        val claims = Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(identityToken)
            .body

        return OAuthLoginRequest(
            email = claims["email"] as String,
            providerId = claims["sub"] as String
        )
    }

    private fun generatePublicKey(identityToken: String): PublicKey {
        val keys = appleClient.getAppleKeys()
        val matchedKey = findMatchedKey(keys, identityToken)

        val nBytes = Base64.getUrlDecoder().decode(matchedKey!!.n)
        val eBytes = Base64.getUrlDecoder().decode(matchedKey.e)

        val n = BigInteger(1, nBytes)
        val e = BigInteger(1, eBytes)

        val publicKeySpec = RSAPublicKeySpec(n, e)
        return KeyFactory.getInstance("RSA").generatePublic(publicKeySpec)
    }

    private fun findMatchedKey(keys: AppleKeys, identityToken: String): Key? {
        val header = identityToken.split(".")[0]
        val decodedHeader = String(Base64.getDecoder().decode(header))

        val headerMap: Map<String, String> = try {
            ObjectMapper().readValue(
                decodedHeader,
                object : TypeReference<Map<String, String>>() {})
        } catch (e: Exception) {
            throw IllegalArgumentException(ErrorMessages.INVALID_IDENTITY_TOKEN.message)
        }
        val key = keys.findKeyByKidAndAlgorithm(headerMap["kid"]!!, headerMap["alg"]!!)
            ?: throw IllegalArgumentException(ErrorMessages.INVALID_IDENTITY_TOKEN.message)

        return key
    }
}