package com.ithub.bigbrotherbackend.telegram.core

import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.model.TelegramNotification
import com.ithub.bigbrotherbackend.util.loggerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import kotlin.coroutines.suspendCoroutine

@Component
class TelegramOrigin(
    @Qualifier("web-client-telegram")
    private val webClient: WebClient
) {

    private val logger by loggerFactory()

    suspend fun send(
        client: TelegramClient,
        notification: TelegramNotification
    ) = suspendCoroutine<TelegramResult> { continuation ->
        CoroutineScope(continuation.context + Dispatchers.IO).launch {
            logger.debug("Send notification!")
        }
    }

}
