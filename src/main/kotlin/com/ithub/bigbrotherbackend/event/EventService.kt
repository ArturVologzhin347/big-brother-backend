package com.ithub.bigbrotherbackend.event

import com.ithub.bigbrotherbackend.event.scud.model.Event
import com.ithub.bigbrotherbackend.event.scud.model.RawEvent
import kotlinx.coroutines.flow.Flow


interface EventService {

    suspend fun reportSkudEvent(rawEvent: RawEvent): Event

    suspend fun queryAllBy(limit: Long?, offset: Long?): Flow<Event>

}
