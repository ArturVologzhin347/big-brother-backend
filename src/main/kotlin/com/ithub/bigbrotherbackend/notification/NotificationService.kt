package com.ithub.bigbrotherbackend.notification

import com.ithub.bigbrotherbackend.error.ApiException
import com.ithub.bigbrotherbackend.respondent.RespondentService
import com.ithub.bigbrotherbackend.skud.model.SkudEventDto
import com.ithub.bigbrotherbackend.telegram.TelegramService
import com.ithub.bigbrotherbackend.util.logFactory
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val telegramService: TelegramService,
    private val respondentService: RespondentService
) {
    private val logger by logFactory()

    suspend fun skudEventNotify(event: SkudEventDto) {
        val respondent = try {
            respondentService.findByCardId(event.cardId)
        } catch (e: ApiException) {
            logger.warn(e.message)
            return
        }

        telegramService.sendSkudEventNotification(event, respondentId = respondent.id())
    }

}
