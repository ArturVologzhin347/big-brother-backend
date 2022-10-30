package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.event.RawEvent
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.skud.model.SkudEventDto
import kotlinx.coroutines.flow.Flow


interface SkudEventService {

    suspend fun sendSkudEvent(rawSkudEvent: RawEvent.RawSkudEvent)

    suspend fun queryAllSkudEvents(
        limit: Long? = null,
        offset: Long? = null
    ): Flow<SkudEvent>

    suspend fun convertToDto(skudEvents: Flow<SkudEvent>): Flow<SkudEventDto>

}
