package com.ithub.bigbrotherbackend.event.reducer

import com.ithub.bigbrotherbackend.crud.SkudEvent
import com.ithub.bigbrotherbackend.event.Event

interface EventReducer {
    suspend fun reduceCrudEvent(skudEvent: SkudEvent): Event
}


