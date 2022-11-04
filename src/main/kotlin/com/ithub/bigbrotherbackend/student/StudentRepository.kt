package com.ithub.bigbrotherbackend.student

import com.ithub.bigbrotherbackend.student.model.Student
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface StudentRepository : CoroutineCrudRepository<Student, String> {

    @Query("SELECT * FROM student LIMIT :limit OFFSET :offset")
    fun queryAllBy(limit: Long?, offset: Long?): Flow<Student>

    @Query("SELECT * FROM student WHERE card_id = :cardId")
    fun findByCardId(cardId: Long?): Mono<Student?>

}
