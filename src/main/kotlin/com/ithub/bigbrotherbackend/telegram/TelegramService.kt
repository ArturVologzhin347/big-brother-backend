package com.ithub.bigbrotherbackend.telegram

import com.fasterxml.jackson.databind.ObjectMapper
import com.ithub.bigbrotherbackend.notification.channel.BaseNotificationChannel
import com.ithub.bigbrotherbackend.notification.channel.SkudEventChannel
import com.ithub.bigbrotherbackend.skud.model.SkudEventDto
import com.ithub.bigbrotherbackend.telegram.model.TelegramClient
import com.ithub.bigbrotherbackend.telegram.model.TelegramConfig
import com.ithub.bigbrotherbackend.telegram.model.TelegramNotification
import com.ithub.bigbrotherbackend.telegram.origin.TelegramOrigin
import com.ithub.bigbrotherbackend.telegram.origin.TelegramResult
import com.ithub.bigbrotherbackend.telegram.repository.TelegramClientRepository
import com.ithub.bigbrotherbackend.telegram.repository.TelegramConifgRepository
import com.ithub.bigbrotherbackend.telegram.repository.TelegramNotificationRepository
import com.ithub.bigbrotherbackend.util.logFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class TelegramService(
    configRepository: TelegramConifgRepository,
    private val clientRepository: TelegramClientRepository,
    private val notificationRepository: TelegramNotificationRepository,
    private val origin: TelegramOrigin,
    private val objectMapper: ObjectMapper
) : BaseNotificationChannel<Long, TelegramClient, TelegramConfig>(clientRepository, configRepository),
    SkudEventChannel {

    private val log by logFactory()

    override suspend fun sendSkudEventNotification(event: SkudEventDto, respondentId: Long) {
        val (client, config) = getClientAndConfig(respondentId)

        if (!client.verified || !config.skudEnabled) {
            return
        }

        val notification = TelegramNotification(
            type = TelegramNotification.Type.SKUD_EVENT,
            payload = objectMapper.writeValueAsString(event),
            telegramClientId = client.id()
        )

        log.debug(notification.toString())

        send(notification, alreadyInBuffer = false, single = false)
    }

    /**
     *
     * @param notification will be sent to Telegram Bot.
     * @param alreadyInBuffer should be true, if notification was taken from buffer.
     * @param single should be true, if you want to send notification once, without saving in buffer if something went wrong.
     *
     */
    private suspend fun send(notification: TelegramNotification, alreadyInBuffer: Boolean, single: Boolean) {
        val client = clientRepository.findById(notification.telegramClientId)

        checkNotNull(client) // Cannot be null, cascade delete enabled.

        val result = origin.sendToTelegramBot(
            chat = client.chat,
            type = notification.type,
            payload = notification.payload
        )

        when (result) {
            is TelegramResult.Failure -> {
                with(result) {
                    when (code) {
                        TelegramResult.FailureCode.CHAT_NOT_FOUND -> {
                            // TODO logout or something
                            log.error("Chat ${client.chat} not found! respondentId: ${client.respondentId}")
                        }

                        TelegramResult.FailureCode.BOT_IS_DOWN -> {
                            log.error("Telegram Bot is down!")
                        }

                        else -> {
                            log.warn("Error on Telegram Bot side.")
                        }
                    }

                    log.warn("Code: $code. Message: $message")
                }

                if (!alreadyInBuffer && !single) {
                    saveToBuffer(notification)
                }
            }

            TelegramResult.Success -> {

                if (alreadyInBuffer) {
                    removeFromBuffer(notification)
                }

            }
        }

    }

    private suspend fun saveToBuffer(notification: TelegramNotification) = withContext(Dispatchers.IO) {
        val saved = notificationRepository.save(notification)
        log.debug("Notification with id: ${saved.id()} was saved in buffer.")
    }

    private suspend fun removeFromBuffer(notification: TelegramNotification) = withContext(Dispatchers.IO) {
        notificationRepository.deleteById(notification.id())
        val unsentNotificationsCount = notificationRepository.count()
        log.debug("Remove notification. Now in buffer $unsentNotificationsCount unsent items.")
    }

}
