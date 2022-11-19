package com.ithub.bigbrotherbackend.telegram.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.model.TelegramNotification
import com.ithub.bigbrotherbackend.util.loggerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Component
class TelegramOrigin(
    @Qualifier("web-client-telegram")
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper
) {

    private val logger by loggerFactory()

    suspend fun send(
        client: TelegramClient,
        notification: TelegramNotification
    ) = suspendCoroutine<TelegramResult> { continuation ->
        CoroutineScope(continuation.context + Dispatchers.IO).launch {

            webClient
                .post()
                .uri("/notification/send")
                .bodyValue(
                    mapOf(
                        "chat" to client.chat,
                        "type" to notification.type,
                        "payload" to notification.payload
                    )
                )
                .retrieve()
                .onStatus(HttpStatus::isError) { response ->
                    response.bodyToMono(Any::class.java)
                        .map { Exception(objectMapper.writeValueAsString(it)) }
                }
                .toBodilessEntity()
                .doOnError {
                    logger.error(it.message)

                    continuation.resume(
                        TelegramResult.Failure(
                            code = TelegramResult.ErrorCode.BOT_INTERNAL_ERROR,
                            message = it.localizedMessage
                        )
                    )
                }
                .doOnSuccess {
                    continuation.resume(TelegramResult.Success)
                }
                .awaitSingle()
        }
    }

}
