package com.ithub.bigbrotherbackend.respondent

import com.ithub.bigbrotherbackend.card.CardRepository
import com.ithub.bigbrotherbackend.card.exception.CardNotAssociatedException
import com.ithub.bigbrotherbackend.respondent.exception.RespondentWithNumberDoesntExists
import com.ithub.bigbrotherbackend.student.StudentRepository
import com.ithub.bigbrotherbackend.student.exception.StudentNotAssociatedException
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class RespondentService(
    private val respondentRepository: RespondentRepository,
    private val cardRepository: CardRepository,
    private val studentRepository: StudentRepository,
) {

    suspend fun findByPhoneNumber(phoneNumber: String): Respondent {
        return respondentRepository.findByPhoneNumber(phoneNumber).awaitSingleOrNull()
            ?: throw RespondentWithNumberDoesntExists(phoneNumber)
    }

    suspend fun findByCardId(id: Long): Respondent {
        val student = studentRepository.findByCardId(cardId = id).awaitSingleOrNull()

        if (student == null) {
            val number = cardRepository.findById(id)?.number ?: "NOT FOUND FOR ID: $id"
            throw CardNotAssociatedException(number)
        }

        return respondentRepository.findByStudentId(student.id).awaitSingleOrNull()
            ?: throw StudentNotAssociatedException(student.id)
    }

}
