package com.ithub.bigbrotherbackend.event.scud

import com.ithub.bigbrotherbackend.event.EventStreamChain
import com.ithub.bigbrotherbackend.event.scud.model.Event
import com.ithub.bigbrotherbackend.notify.telegram.TelegramApi

class NotifyEventStreamChain(
    private val telegramApi: TelegramApi
) : EventStreamChain {

    override suspend fun run(event: Event) {
        telegramApi.sendBroadcastNotification(event) // test
    }

}
