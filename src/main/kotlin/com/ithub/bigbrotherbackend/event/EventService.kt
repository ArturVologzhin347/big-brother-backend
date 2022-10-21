package com.ithub.bigbrotherbackend.event

import kotlinx.coroutines.flow.Flow

interface EventService {

    // TODO expend filter parameters
    suspend fun queryAllBy(limit: Int?, offset: Int?): Flow<Event>

    suspend fun saveEvent(event: Event): Event

}
