package com.ithub.bigbrotherbackend.skud.core

import com.ithub.bigbrotherbackend.card.CardService
import com.ithub.bigbrotherbackend.event.handle.EventStream
import com.ithub.bigbrotherbackend.skud.SkudEventRepository
import com.ithub.bigbrotherbackend.skud.chain.SkudEventNotificationChain
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SkudEventConfig {

    @Bean
    fun skudEventAdapter(
        cardService: CardService,
        skudEventRepository: SkudEventRepository
    ) = SkudEventAdapter(
        transformer = SkudEventTransformer(cardService),
        skudEventRepository = skudEventRepository,
        stream = EventStream(
            SkudEventNotificationChain()
        )
    )

}