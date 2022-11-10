package com.ithub.bigbrotherbackend.telegram.origin

import com.ithub.bigbrotherbackend.config.WebClientConfig
import com.ithub.bigbrotherbackend.telegram.model.TelegramNotification
import com.ithub.bigbrotherbackend.util.logFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Component
class TelegramOrigin(
    @Qualifier(WebClientConfig.QUALIFIER_WEB_CLIENT_TELEGRAM)
    private val webClient: WebClient
) {

    private val log by logFactory()

    /**
     * Common request to Telegram Bot. All cases must be handled on its side.
     *
     * @param chat is unique ID for telegram.
     * @param type of notification. Should be handled in telegram bot.
     * @param payload is current data for handle current type.
     *
     */
    suspend fun sendToTelegramBot(
        chat: Long,
        type: TelegramNotification.Type,
        payload: String
    ) = suspendCoroutine<TelegramResult> {
        CoroutineScope(it.context + Dispatchers.IO).launch {
            webClient
                .post()
                .uri(TELEGRAM_ENDPOINT_URL)
                .bodyValue(
                    mapOf(
                        "chat" to chat,
                        "type" to type.name,
                        "payload" to payload
                    )
                )
                .awaitExchange { response ->
                    if (response.statusCode() == HttpStatus.OK) {
                        log.debug("Telegram Notification has been sent. chat: $chat, type: $type;\npayload: $payload")
                        it.resume(TelegramResult.Success)
                    } else {
                        val failure = when (val body: Any = response.awaitBody()) { // TODO
                            else -> TelegramResult.Failure(
                                code = TelegramResult.FailureCode.UNKNOWN,
                                message = body.toString()
                            )
                        }

                        it.resume(failure)
                    }
                }
        }
    }

    companion object {
        private const val TELEGRAM_ENDPOINT_URL = "/skud"
    }

}
