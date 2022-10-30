package com.ithub.bigbrotherbackend.skud.chain

import com.ithub.bigbrotherbackend.event.handle.EventStreamChain
import com.ithub.bigbrotherbackend.skud.model.SkudEvent

class SkudEventNotificationChain : EventStreamChain<SkudEvent> {

    override suspend fun run(event: SkudEvent) {
        println(event)
    }

}
