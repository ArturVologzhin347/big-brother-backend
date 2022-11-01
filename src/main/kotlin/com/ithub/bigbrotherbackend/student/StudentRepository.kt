package com.ithub.bigbrotherbackend.student

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface StudentRepository : CoroutineCrudRepository<Student, Long> {

    @Query("SELECT * FROM student LIMIT :limit OFFSET :offset")
    fun queryAllBy(limit: Long?, offset: Long?): Flow<Student>

}
