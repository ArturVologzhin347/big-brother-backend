package com.ithub.bigbrotherbackend.notification.channel

import com.ithub.bigbrotherbackend.skud.model.SkudEventDto

interface SkudEventChannel {
    suspend fun sendSkudEventNotification(event: SkudEventDto, respondentId: Long)
}
