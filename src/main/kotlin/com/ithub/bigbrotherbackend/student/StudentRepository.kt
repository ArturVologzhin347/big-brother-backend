package com.ithub.bigbrotherbackend.student

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface StudentRepository : CoroutineCrudRepository<Student, String> {

    @Query("SELECT * FROM student WHERE card_id=:?")
    fun findByCardId(id: Long): Mono<Student>

}
