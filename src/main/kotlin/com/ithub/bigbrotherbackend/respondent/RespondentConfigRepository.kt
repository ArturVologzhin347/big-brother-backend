package com.ithub.bigbrotherbackend.respondent

import com.ithub.bigbrotherbackend.respondent.model.RespondentConfig
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface RespondentConfigRepository : CoroutineCrudRepository<RespondentConfig, Long> {

    @Query("SELECT * FROM respondent_config WHERE respondent_id= :?")
    fun findOneBy(respondentId: Long): Mono<RespondentConfig>

}
