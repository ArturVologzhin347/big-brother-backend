package com.ithub.bigbrotherbackend.event

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class EventServiceImpl(
    private val eventRepository: EventRepository
) : EventService {

    override suspend fun queryAllBy(limit: Int?, offset: Int?): Flow<Event> {
        return eventRepository.queryAllBy(limit, offset)
    }

    override suspend fun saveEvent(event: Event): Event {
        return eventRepository.save(event)
    }

}
