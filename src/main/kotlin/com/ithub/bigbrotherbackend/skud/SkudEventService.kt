package com.ithub.bigbrotherbackend.skud

import com.ithub.bigbrotherbackend.skud.model.RawSkudEvent
import com.ithub.bigbrotherbackend.skud.model.SkudEvent
import com.ithub.bigbrotherbackend.skud.model.SkudEventDto
import com.ithub.bigbrotherbackend.skud.model.toDto
import com.ithub.bigbrotherbackend.student.StudentRepository
import com.ithub.bigbrotherbackend.student.model.toDto
import com.ithub.bigbrotherbackend.telegram.TelegramOrigin
import com.ithub.bigbrotherbackend.util.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SkudEventService(
    private val skudEventRepository: SkudEventRepository,
    private val skudEventTransformer: SkudEventTransformer,
    private val studentRepository: StudentRepository,
    private val telegramOrigin: TelegramOrigin
) {

    suspend fun findAll(
        pageRequest: PageRequest
    ): Page<SkudEventDto> {
        val content = buildDto(skudEventRepository.findBy(pageRequest)).await()
        val totalCount = skudEventRepository.count()
        return PageImpl(content, pageRequest, totalCount)
    }

    suspend fun handleEvent(rawSkudEvent: RawSkudEvent): SkudEvent {
        return skudEventRepository.save(skudEventTransformer.transform(rawSkudEvent)).apply {

            // TODO improve
            val dto = buildDto(this)
            telegramOrigin.notifyAboutSkudEvent(dto)
        }
    }

    suspend fun buildDto(skudEvents: Flow<SkudEvent>): Flow<SkudEventDto> {
        return skudEvents.map { buildDto(it) }
    }

    suspend fun buildDto(skudEvent: SkudEvent): SkudEventDto {
        val student = studentRepository.findByCardId(skudEvent.cardId).awaitSingleOrNull()?.toDto()
        return skudEvent.toDto(student)
    }

}

