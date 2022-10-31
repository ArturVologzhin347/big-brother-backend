package com.ithub.bigbrotherbackend.skud.chain

import com.ithub.bigbrotherbackend.config.WebClientConfig
import com.ithub.bigbrotherbackend.event.handle.EventStreamChain
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.reactive.function.client.WebClient

class SkudEventNotificationChain(
    private val telegramWebClient: WebClient
) : EventStreamChain<SkudEvent> {

    override suspend fun run(event: SkudEvent) {
        println(event)
    }

}
