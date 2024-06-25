package com.ssu.eatssu.domain.oauth.presentation.dto

data class AppleKeys(
    val keys: List<Key>
) {
    fun findKeyByKidAndAlgorithm(kid: String, algorithm: String): Key? {
        return keys.find { it.kid == kid && it.alg == algorithm }
    }
}

data class Key(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String
)