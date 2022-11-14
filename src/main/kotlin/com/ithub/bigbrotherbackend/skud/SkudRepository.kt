package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface SkudRepository : CoroutineCrudRepository<SkudEvent, Long> {

    @Query("SELECT * FROM skud_event WHERE student_id=:1? ORDER BY timestamp DESC LIMIT :2? OFFSET :3?")
    fun queryAllBy(studentId: String, limit: Int?, offset: Int?): Flow<SkudEvent>

    @Query("SELECT * FROM skud_event ORDER BY timestamp DESC LIMIT :1? OFFSET :2?")
    fun queryAll(limit: Int, offset: Int): Flow<SkudEvent>

    @Query("SELECT COUNT(*) FROM skud_event WHERE student_id=:?")
    fun countAllBy(studentId: String): Mono<Long>

    @Query("SELECT * FROM skud_event  WHERE student_id=:? ORDER BY timestamp DESC LIMIT 1")
    fun findLastBy(studentId: String): Mono<SkudEvent>

}
