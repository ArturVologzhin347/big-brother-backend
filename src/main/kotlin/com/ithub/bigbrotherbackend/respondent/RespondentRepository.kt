package com.ithub.bigbrotherbackend.respondent

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Mono

interface RespondentRepository : CoroutineCrudRepository<Respondent, Long> {

    @Query("SELECT * FROM respondent WHERE phone_number=:phoneNumber")
    fun findByPhoneNumber(phoneNumber: String): Mono<Respondent?>

}
