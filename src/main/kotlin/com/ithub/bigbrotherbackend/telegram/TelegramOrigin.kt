package com.ithub.bigbrotherbackend.telegram

import com.ithub.bigbrotherbackend.config.WebClientConfig
import com.ithub.bigbrotherbackend.skud.model.SkudEventDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class TelegramOrigin(
    @Qualifier(WebClientConfig.QUALIFIER_WEB_CLIENT_TELEGRAM)
    private val webClient: WebClient
) {

    suspend fun notifyAboutSkudEvent(event: SkudEventDto) {
        webClient
            .post()
            .uri("/skud")
            .bodyValue(event)
            .awaitExchange { clientResponse ->
                println(clientResponse.statusCode())
            }
    }



}
