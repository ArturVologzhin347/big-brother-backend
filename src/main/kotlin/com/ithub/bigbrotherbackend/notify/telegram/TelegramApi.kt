package com.ithub.bigbrotherbackend.notify.telegram

import com.ithub.bigbrotherbackend.event.scud.model.Event

interface TelegramApi {
    suspend fun sendBroadcastNotification(event: Event)
}

