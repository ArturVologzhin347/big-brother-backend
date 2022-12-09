package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
interface SkudRepository : CoroutineCrudRepository<SkudEvent, Long> {

    @Query(
        """
            SELECT timestamp FROM skud_event
             WHERE student_id LIKE COALESCE(:1?, '%') 
             ORDER BY timestamp LIMIT 1
        """
    )
    fun findMostOlderTimestampBy(studentId: String?): Mono<LocalDateTime>

    @Query("SELECT * FROM skud_event WHERE timestamp > :2? ORDER BY timestamp DESC")
    fun queryAllAfter(timestamp: LocalDateTime): Flow<SkudEvent>

    @Query(
        """
        SELECT * FROM skud_event WHERE
        student_id LIKE COALESCE(:1?, '%') AND
        timestamp < :2?
        ORDER BY timestamp DESC
        LIMIT :3?
        OFFSET 0
        """
    )
    fun queryAllBy(studentId: String?, older: LocalDateTime, limit: Int): Flow<SkudEvent>

    @Query("SELECT COUNT(*) FROM skud_event WHERE student_id=:?")
    fun countAllBy(studentId: String): Mono<Long>

    @Query("SELECT * FROM skud_event  WHERE student_id=:? ORDER BY timestamp DESC LIMIT 1")
    fun findLastBy(studentId: String): Mono<SkudEvent>

}
