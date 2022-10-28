package com.ithub.bigbrotherbackend.event

import com.ithub.bigbrotherbackend.event.scud.model.Event

interface EventStreamChain {
    suspend fun run(event: Event)
}