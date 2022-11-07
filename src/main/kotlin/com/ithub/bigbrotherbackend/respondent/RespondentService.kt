package com.ithub.bigbrotherbackend.respondent

import com.ithub.bigbrotherbackend.respondent.exception.RespondentWithNumberDoesntExists
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class RespondentService(
    private val respondentRepository: RespondentRepository
) {

    suspend fun findRespondentByPhoneNumber(phoneNumber: String): Respondent {
        return respondentRepository
            .findByPhoneNumber(phoneNumber)
            .awaitSingle() ?: throw RespondentWithNumberDoesntExists(phoneNumber)
    }


}