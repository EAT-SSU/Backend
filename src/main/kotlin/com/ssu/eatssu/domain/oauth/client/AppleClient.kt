package com.ssu.eatssu.domain.oauth.client

import com.ssu.eatssu.domain.oauth.presentation.dto.AppleKeys
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AppleClient {
    val BASE_URL = "https://appleid.apple.com"
    fun getAppleKeys(): AppleKeys {
        val webClient = WebClient.create(BASE_URL)

        return webClient.get()
            .uri("auth/keys")
            .retrieve()
            .bodyToMono(AppleKeys::class.java)
            .block()!!
    }
}