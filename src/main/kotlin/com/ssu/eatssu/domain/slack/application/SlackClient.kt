package com.ssu.eatssu.domain.slack.application

import com.slack.api.Slack
import com.ssu.eatssu.domain.slack.entity.SlackChannel
import com.ssu.eatssu.domain.slack.entity.SlackProperties
import org.springframework.stereotype.Service

@Service
class SlackClient(
    private val slackProperties: SlackProperties
) {

    fun sendSlackMessage(
        message: String,
        channel: SlackChannel
    ) {
        val channelAddress = channel.koreanName

        try {
            val client = Slack.getInstance().methods(slackProperties.token);
            client.chatPostMessage {
                it.channel(channelAddress)
                    .text(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}