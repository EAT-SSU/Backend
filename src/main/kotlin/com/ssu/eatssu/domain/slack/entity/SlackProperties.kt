package com.ssu.eatssu.domain.slack.entity

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("slack")
data class SlackProperties(val token: String)
