package com.ithub.bigbrotherbackend.event

import com.ithub.bigbrotherbackend.event.scud.model.Event
import com.ithub.bigbrotherbackend.event.scud.EventManager
import com.ithub.bigbrotherbackend.event.scud.model.RawEvent
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class EventServiceImpl(
    private val eventRepository: EventRepository,
    private val eventManager: EventManager
) : EventService {

    override suspend fun reportSkudEvent(rawEvent: RawEvent): Event {
        return eventManager.handleEvent(rawEvent)
    }

    override suspend fun queryAllBy(limit: Long?, offset: Long?): Flow<Event> {
        return eventRepository.queryAllBy(limit, offset)
    }

}
