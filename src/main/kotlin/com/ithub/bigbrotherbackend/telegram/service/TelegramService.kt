package com.ithub.bigbrotherbackend.telegram.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.ithub.bigbrotherbackend.respondent.model.RespondentConfig
import com.ithub.bigbrotherbackend.skud.dto.SkudEventDisplayDto
import com.ithub.bigbrotherbackend.telegram.core.TelegramOrigin
import com.ithub.bigbrotherbackend.telegram.core.TelegramResult
import com.ithub.bigbrotherbackend.telegram.core.TelegramResult.ErrorCode.BOT_IS_DOWN
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.model.TelegramNotification
import com.ithub.bigbrotherbackend.telegram.repository.TelegramNotificationRepository
import com.ithub.bigbrotherbackend.util.loggerFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class TelegramService(
    private val clientService: TelegramClientService,
    private val notificationRepository: TelegramNotificationRepository,
    private val origin: TelegramOrigin,
    private val objectMapper: ObjectMapper
) {

    private val logger by loggerFactory()

    suspend fun sendSkudEvent(config: RespondentConfig, event: SkudEventDisplayDto) {
        clientService.findAllClientsBy(respondentConfigId = config.id()).onEach { client ->

            if (!config.skudEnabled) {
                return@onEach
            }

            send(
                client = client,
                notification = TelegramNotification(
                    type = TelegramNotification.Type.SKUD_EVENT,
                    payload = objectMapper.writeValueAsString(event),
                    clientId = client.id()
                ),
                alreadyBuffered = false,
                once = false
            )
        }.collect()
    }

    suspend fun tryResendBufferedNotifications() = withContext(Dispatchers.IO) {
        logger.info("Unsent messages count: ${notificationRepository.count()}. Try send buffered notifications...")
        notificationRepository
            .findAll()
            .mapNotNull { it to (clientService.findClientBy(id = it.clientId) ?: return@mapNotNull null) }
            .onEach { (notification, client) ->
                send(
                    client = client,
                    notification = notification,
                    alreadyBuffered = true,
                    once = false,
                )
            }.collect()
    }

    /**
     * @param client destination to send notification
     * @param notification data for send message in telegram
     * @param alreadyBuffered must be true, if notification was gotten from buffer
     * @param once should be true, if you do not want to interact with buffer, when something went wrong
     */
    private suspend fun send(
        client: TelegramClient,
        notification: TelegramNotification,
        alreadyBuffered: Boolean,
        once: Boolean
    ) {
        when (val result = origin.send(client, notification)) {
            is TelegramResult.Failure -> {

                when (result.code) {
                    BOT_IS_DOWN -> {
                        logger.error("TELEGRAM BOT IS DOWN!")
                    }

                    else -> {
                        logger.warn("${result.code.name} - ${result.message}")
                    }
                }

                if (!alreadyBuffered && !once) {
                    notificationRepository.save(notification)
                }

            }

            TelegramResult.Success -> {

                if (alreadyBuffered && !once) {
                    notificationRepository.deleteById(id = notification.id())
                }

            }
        }

    }

}
