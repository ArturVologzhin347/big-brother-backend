package com.ithub.bigbrotherbackend.notification

import com.ithub.bigbrotherbackend.respondent.RespondentService
import com.ithub.bigbrotherbackend.skud.dto.SkudEventDisplayDto
import com.ithub.bigbrotherbackend.telegram.service.TelegramService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val respondentService: RespondentService,
    private val telegramService: TelegramService
    ) {

    suspend fun sendSkudEvent(event: SkudEventDisplayDto) {
        respondentService
            .findRespondentsBy(studentId = event.student.id)
            .map { it to respondentService.findRespondentConfigBy(respondentId = it.id()).awaitSingle() }
            .onEach { (_, config) ->
                telegramService.sendSkudEvent(config, event)
            }.collect()
    }

}
