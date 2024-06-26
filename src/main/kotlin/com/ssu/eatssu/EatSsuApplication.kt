package com.ssu.eatssu

import com.ssu.eatssu.domain.slack.entity.SlackProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SlackProperties::class)
class EatSsuKotlinApplication

fun main(args: Array<String>) {
    runApplication<EatSsuKotlinApplication>(*args)
}
