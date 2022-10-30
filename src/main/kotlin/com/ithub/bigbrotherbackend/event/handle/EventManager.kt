package com.ithub.bigbrotherbackend.event.handle

import com.ithub.bigbrotherbackend.event.RawEvent
import com.ithub.bigbrotherbackend.skud.core.SkudEventAdapter
import org.springframework.stereotype.Component

@Component
class EventManager(
    private val skudEventAdapter: SkudEventAdapter
) {

    suspend fun sendEvent(rawEvent: RawEvent) = when (rawEvent) {
        is RawEvent.RawSkudEvent -> skudEventAdapter.makeEvent(rawEvent)
    }

}
