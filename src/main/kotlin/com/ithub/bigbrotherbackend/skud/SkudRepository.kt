package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface SkudRepository : CoroutineCrudRepository<SkudEvent, Long> {

    @Query("SELECT * FROM skud_event WHERE student_id=:1? ORDER BY timestamp DESC LIMIT :2? OFFSET :3? ")
    fun queryAllByStudentId(studentId: String, limit: Int?, offset: Int?): Flow<SkudEvent>

    @Query("SELECT * FROM skud_event ORDER BY timestamp DESC LIMIT :1? OFFSET :2?")
    fun queryAll(limit: Int, offset: Int): Flow<SkudEvent>

    @Query("SELECT COUNT(*) FROM skud_event WHERE student_id=:?")
    fun countAllByStudentId(studentId: String): Mono<Long>

}
