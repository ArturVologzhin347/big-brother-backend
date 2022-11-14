package com.ithub.bigbrotherbackend.notification.channel

import com.ithub.bigbrotherbackend.skud.model.SkudEvent

interface SkudEventChannel {
    suspend fun sendSkudEvent(event: SkudEvent)
}
