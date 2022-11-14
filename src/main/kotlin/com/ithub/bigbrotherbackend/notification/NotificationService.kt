package com.ithub.bigbrotherbackend.notification

import com.ithub.bigbrotherbackend.notification.channel.SkudEventChannel
import com.ithub.bigbrotherbackend.respondent.RespondentService
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val respondentService: RespondentService,

    ) : SkudEventChannel {

    override suspend fun sendSkudEvent(event: SkudEvent) {
        respondentService
            .findRespondentsBy(studentId = event.studentId)
            .map { it to respondentService.findRespondentConfigBy(respondentId = it.id()).awaitSingle() }
            .onEach { (respondent, config) ->


            }.collect()
    }
    
}
