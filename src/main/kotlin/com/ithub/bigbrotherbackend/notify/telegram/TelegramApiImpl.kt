package com.ithub.bigbrotherbackend.notify.telegram

import com.ithub.bigbrotherbackend.config.WebClientConfig
import com.ithub.bigbrotherbackend.event.scud.model.Event
import com.ithub.bigbrotherbackend.util.logFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class TelegramApiImpl(

    @Qualifier(WebClientConfig.QUALIFIER_WEB_CLIENT_TELEGRAM)
    private val webClient: WebClient

) : TelegramApi {

    private val log by logFactory()

    override suspend fun sendBroadcastNotification(event: Event) {
        // TODO send message to telegram
        log.debug("Send notification to tg bot")
    }

}
