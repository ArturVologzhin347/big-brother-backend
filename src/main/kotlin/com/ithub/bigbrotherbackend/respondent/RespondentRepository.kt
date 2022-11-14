package com.ithub.bigbrotherbackend.respondent

import com.ithub.bigbrotherbackend.respondent.model.Respondent
import com.ithub.bigbrotherbackend.respondent.model.RespondentConfig
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RespondentRepository : CoroutineCrudRepository<Respondent, Long> {

    @Transactional
    fun findRespondentsBy(studentId: String): Flow<Respondent> {
        return findAllById(findRespondentIdsBy(studentId))
    }

    @Query("SELECT (respondent_id) FROM student_respondent WHERE student_id=:?")
    fun findRespondentIdsBy(studentId: String): Flow<Long>

}
