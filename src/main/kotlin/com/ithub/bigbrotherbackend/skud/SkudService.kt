package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.card.CardService
import com.ithub.bigbrotherbackend.skud.dto.SkudEventDisplayDto
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.student.StudentService
import com.ithub.bigbrotherbackend.student.dto.StudentDto
import com.ithub.bigbrotherbackend.util.apiError
import com.ithub.bigbrotherbackend.util.await
import com.ithub.bigbrotherbackend.util.loggerFactory
import com.ithub.bigbrotherbackend.util.toLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class SkudService(
    private val cardService: CardService,
    private val studentService: StudentService,
    private val skudRepository: SkudRepository
) {

    private val logger by loggerFactory()

    suspend fun queryAllWithPagination(
        studentId: String?,
        limit: Int,
        offset: Int
    ): Page<SkudEventDisplayDto> {
        val events = queryAll(studentId, limit, offset).toDisplayedDto().await()
        val total = countAll(studentId)
        return PageImpl(events, PageRequest.of(offset, limit), total)
    }

    suspend fun queryAll(
        studentId: String?,
        limit: Int,
        offset: Int
    ): Flow<SkudEvent> {
        return if (studentId == null) {
            skudRepository.queryAll(limit, offset)
        } else {
            skudRepository.queryAllByStudentId(studentId, limit, offset)
        }
    }

    suspend fun countAll(studentId: String?): Long {
        return if (studentId == null) {
            skudRepository.count()
        } else {
            skudRepository.countAllByStudentId(studentId).awaitSingle()
        }

    }

    suspend fun Flow<SkudEvent>.toDisplayedDto(): Flow<SkudEventDisplayDto> {
        return map {
            val student = StudentDto.from(
                studentService.findById(it.studentId)
                    ?: apiError("Student cannot be null")
            )
            SkudEventDisplayDto.from(it, student)
        }
    }

    suspend fun getLastByStudentId(studentId: String): SkudEvent? {
        return skudRepository.findLastByStudentId(studentId).awaitSingleOrNull()
    }

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
