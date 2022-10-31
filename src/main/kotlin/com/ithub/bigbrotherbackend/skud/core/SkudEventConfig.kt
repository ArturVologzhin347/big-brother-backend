package com.ithub.bigbrotherbackend.skud.core

import com.ithub.bigbrotherbackend.card.CardService
import com.ithub.bigbrotherbackend.config.WebClientConfig
import com.ithub.bigbrotherbackend.event.handle.EventStream
import com.ithub.bigbrotherbackend.skud.SkudEventRepository
import com.ithub.bigbrotherbackend.skud.chain.SkudEventNotificationChain
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class SkudEventConfig {

    @Bean
    fun skudEventAdapter(
        cardService: CardService,
        skudEventRepository: SkudEventRepository,
        @Qualifier(WebClientConfig.QUALIFIER_WEB_CLIENT_TELEGRAM) webClient: WebClient
    ) = SkudEventAdapter(
        transformer = SkudEventTransformer(cardService),
        skudEventRepository = skudEventRepository,
        stream = EventStream(
            SkudEventNotificationChain(telegramWebClient = webClient)
        )
    )

}
