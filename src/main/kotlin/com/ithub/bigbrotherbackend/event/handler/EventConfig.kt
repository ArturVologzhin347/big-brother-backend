package com.ithub.bigbrotherbackend.event.handler

import com.ithub.bigbrotherbackend.Profiles
import com.ithub.bigbrotherbackend.card.CardRepository
import com.ithub.bigbrotherbackend.event.EventRepository
import com.ithub.bigbrotherbackend.event.scud.*
import com.ithub.bigbrotherbackend.notify.telegram.TelegramApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
class EventConfig {

    // TODO production bean

    @Bean
    @Profile(Profiles.DEV)
    fun eventManager(
        cardRepository: CardRepository,
        eventRepository: EventRepository,
        telegramApi: TelegramApi
    ) = EventManager(
        eventTransformer = EventTransformer(cardRepository),
        eventProducer = EventProducer(eventRepository),
        stream = EventStream(
            NotifyEventStreamChain(telegramApi)
        )
    )


}
