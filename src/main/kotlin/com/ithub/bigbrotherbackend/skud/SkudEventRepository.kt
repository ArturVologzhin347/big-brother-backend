package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SkudEventRepository : CoroutineCrudRepository<SkudEvent, Long> {

    @Query("SELECT * FROM skud_event LIMIT :limit OFFSET :offset")
    fun queryAllBy(limit: Long? = null, offset: Long? = null): Flow<SkudEvent>

}
