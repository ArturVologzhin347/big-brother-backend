package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.card.CardService
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.student.StudentService
import com.ithub.bigbrotherbackend.util.apiError
import com.ithub.bigbrotherbackend.util.loggerFactory
import com.ithub.bigbrotherbackend.util.toLocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class SkudService(
    private val cardService: CardService,
    private val studentService: StudentService,
    private val skudRepository: SkudRepository
) {

    private val logger by loggerFactory()

    suspend fun acceptSkudEvent(
        cardNumber: String,
        type: SkudEvent.Type,
        timestamp: Long
    ) {

        val card = cardService.findByNumber(cardNumber) ?: apiError(
            message = "Card with number $cardNumber doesn't exists.",
            code = "CARD_NOT_EXISTS",
            status = HttpStatus.NOT_FOUND
        )

        val student = studentService.findByCardId(card.id()) ?: apiError(
            message = "Not associated student with card $card",
            code = "STUDENT_NOT_EXISTS",
            status = HttpStatus.NOT_FOUND
        )

        val skudEvent = skudRepository.save(
            SkudEvent(
                type = type,
                timestamp = timestamp.toLocalDateTime(),
                studentId = student.id()
            )
        )

        sendEventToNotificationChannels(skudEvent)
    }

    private suspend fun sendEventToNotificationChannels(event: SkudEvent) {
        logger.debug(event.toString())
        // TODO notification channels implementation
    }

}
