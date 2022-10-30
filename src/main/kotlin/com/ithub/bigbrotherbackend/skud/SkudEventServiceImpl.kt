package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.event.RawEvent
import com.ithub.bigbrotherbackend.event.handle.EventManager
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.skud.model.SkudEventDto
import com.ithub.bigbrotherbackend.skud.model.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class SkudEventServiceImpl(
    private val skudEventRepository: SkudEventRepository,
    private val eventManager: EventManager
) : SkudEventService {

    override suspend fun sendSkudEvent(rawSkudEvent: RawEvent.RawSkudEvent) {
        eventManager.sendEvent(rawSkudEvent)
    }

    override suspend fun queryAllSkudEvents(limit: Long?, offset: Long?): Flow<SkudEvent> {
        return skudEventRepository.queryAllBy(limit, offset)
    }

    override suspend fun convertToDto(skudEvents: Flow<SkudEvent>): Flow<SkudEventDto> {
        return skudEvents.map { skudEvent -> skudEvent.toDto() }
    }

}