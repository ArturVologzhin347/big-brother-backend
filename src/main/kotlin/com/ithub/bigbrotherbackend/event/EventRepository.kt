package com.ithub.bigbrotherbackend.event

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : CoroutineCrudRepository<Event, Long> {

    @Query("SELECT * FROM event LIMIT :limit OFFSET :offset")
    fun queryAllBy(limit: Int?, offset: Int?): Flow<Event>

}
