package com.ssu.eatssu

import com.ssu.eatssu.domain.slack.entity.SlackProperties
import com.ssu.eatssu.global.aws.S3Properties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SlackProperties::class, S3Properties::class)
class EatSsuKotlinApplication

fun main(args: Array<String>) {
    runApplication<EatSsuKotlinApplication>(*args)
}
