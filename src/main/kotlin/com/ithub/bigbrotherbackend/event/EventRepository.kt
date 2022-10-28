package com.ithub.bigbrotherbackend.event

import com.ithub.bigbrotherbackend.event.scud.model.Event
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EventRepository : CoroutineCrudRepository<Event, Long> {

    @Query("SELECT * FROM event LIMIT :limit OFFSET :offset")
    fun queryAllBy(limit: Long?, offset: Long?): Flow<Event>

}
