package com.ithub.bigbrotherbackend.respondent

import com.ithub.bigbrotherbackend.respondent.model.Respondent
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RespondentRepository : CoroutineCrudRepository<Respondent, Long> {

}
