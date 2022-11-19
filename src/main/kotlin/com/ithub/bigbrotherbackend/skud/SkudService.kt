package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.card.CardService
import com.ithub.bigbrotherbackend.notification.NotificationService
import com.ithub.bigbrotherbackend.skud.dto.SkudEventDisplayDto
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.student.StudentRepository
import com.ithub.bigbrotherbackend.student.dto.StudentDto
import com.ithub.bigbrotherbackend.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SkudService(
    private val cardService: CardService,
    private val notificationService: NotificationService,
    private val studentRepository: StudentRepository,
    private val skudRepository: SkudRepository,
    cache: CacheManager
) {

    private val logger by loggerFactory()
    private val cacheLastEvents by cache.cached("last_events")

    @Cacheable("last_events", key = "#studentId")
    fun findLast(studentId: String): Mono<SkudEvent> {
        return skudRepository.findLastBy(studentId).cache()
    }

    suspend fun queryAllWithPagination(
        studentId: String?,
        limit: Int,
        offset: Int
    ): Page<SkudEventDisplayDto> {
        val events = queryAllBy(studentId, limit, offset).toDisplayDto().await()
        val total = countAllBy(studentId)
        return PageImpl(events, PageRequest.of(offset, limit), total)
    }

    suspend fun queryAllBy(
        studentId: String?,
        limit: Int,
        offset: Int
    ): Flow<SkudEvent> {
        return if (studentId == null) {
            skudRepository.queryAll(limit, offset)
        } else {
            skudRepository.queryAllBy(studentId, limit, offset)
        }
    }

    suspend fun countAllBy(studentId: String?): Long {
        return if (studentId == null) {
            skudRepository.count()
        } else {
            skudRepository.countAllBy(studentId).awaitSingle()
        }
    }

    suspend fun Flow<SkudEvent>.toDisplayDto(): Flow<SkudEventDisplayDto> {
        return map { it.toDisplayDto() }
    }


    suspend fun SkudEvent.toDisplayDto(): SkudEventDisplayDto {
        return SkudEventDisplayDto.from(
            event = this,
            student = StudentDto.from(
                studentRepository.findById(this.studentId) ?: apiError("Student cannot be null")
            )
        )
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

        val student = studentRepository
            .findByCardId(card.id())
            .awaitSingleOrNull() ?: apiError(
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

    protected suspend fun sendEventToNotificationChannels(event: SkudEvent) {
        cacheLastEvents.evict(event.studentId)
        notificationService.sendSkudEvent(event.toDisplayDto())
    }

}
